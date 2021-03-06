package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import shared.Vector2f;
import shared.PacketUpdatePlayerPos;

class GameServer
{
	private static GameServer gameServer = null;
//
//	      this.stage1builder = new Stage1Builder(gridSize);
//        this.stage2builder = new Stage2Builder(gridSize);
//        this.stage3builder = new Stage3Builder(gridSize);
//


	protected volatile GameBoard gameBoard;

	protected GameState currentState;

	protected volatile Map<Integer, MPPlayer> players;
	protected volatile Network network;
	private GameCycleThread thread;
	private Stage1Factory stage1factory;

	private int gameLevel;
	private int counter;
	private boolean readyForNextFrame;
	private Chain chain1;
	private Chain chain2;
	private Chain chain3;
	private Chain chain4;
	public boolean enableMovement = false;

	public ItemComponent powerUp;

	public ItemComponent everyItem;

	public Item dash;
	public Item teleport;
	public Item jump;
	public Item slowDown;
	public Item past;

	public Item bomb;


	public Item wall;



	public ItemList items;


	private int levelChangeCounter;


	private boolean updateBoard = false;

	
	private GameServer()
	{

		powerUp = new ItemGroup("Powerup", "Folder that has all powerups that have been used");

		everyItem = new ItemGroup("Item list", "This is the list that hold data of PowerUps, Walls and Bombs");



		dash = new Item("Dash", "Player moves fast. ", 0);
		teleport = new Item("Teleport", "Player teleports. ", 0);
		slowDown = new Item("SlowDown", "Enemy get slowed down. ", 0);
		jump = new Item("Jump", "Player is capable of jumping over walls. ", 0);
		past = new Item("Past", "Player is time traveler. ", 0);


		bomb = new Item("Bomb", "Dangerous item. ", 0);
		wall = new Item("Wall", "Blocks players path. ", 0);


		everyItem.add(powerUp);

		powerUp.add(dash);
		powerUp.add(teleport);
		powerUp.add(slowDown);
		powerUp.add(jump);
		powerUp.add(past);

		everyItem.add(bomb);
		everyItem.add(wall);



		items = new ItemList(everyItem);



		counter = 0;

		levelChangeCounter = 0;

		//Init Connection
		if (!initConnection())
		{
			System.err.println("ERROR Connecting to host");
			return;
		}

		gameLevel = 2;

		this.players = new HashMap<Integer, MPPlayer>();

		setGameLevel();

		this.thread = new GameCycleThread();
		this.thread.start();

		chain1 = new GenerateWalls();
		chain3 = new GenerateDesWall();
		chain2 = new GenerateTrap();
		readyForNextFrame = true;
		chain1.setNextChain(chain2);
		chain2.setNextChain(chain3);

		GameState waitForPlayersState = new StateWaitForPlayers();
		GameState playGameState = new StatePlayGame();
		GameState endingGameState = new StateEndingGame();
		GameState changingMapState = new StateChangingMap();
		
		waitForPlayersState.setNextState(playGameState);
		playGameState.setNextState(endingGameState);
		endingGameState.setNextState(changingMapState);
		changingMapState.setNextState(waitForPlayersState);	
		currentState = waitForPlayersState;
		
		
	}
	
	protected GameServer getContext()
	{
		return this;
	}
	
	public boolean checkIfPlayerDead(){
		for(MPPlayer p : players.values())
		{
			if(p.coordinate != null){
				if(p.health < 1 && p.canDie){
					p.deathCounter +=1;
					//System.out.println("Mires");
					//setGameLevel();
					return true;
				}
			}
		}
		return false;

	}
	
	public String getWinner(){
		boolean firstCycle = true;
		for(MPPlayer p : players.values())
		{
			if (!firstCycle)
			{
				if(p.coordinate != null){
					if(p.health > 0){
						//setGameLevel();
						return String.valueOf(p.c.getID());
					}
				}
			}
			firstCycle = false;
		}
		return "None";

	}
	
	public int getConnectedPlayerCount(){
		return players.size();

	}
	//nustatome sekancia busena
	public void setState(GameState nextState) 
	{
		this.currentState = nextState;		
	}
	
	
	public void setGameLevel(){



		if(gameLevel == 3)
			gameLevel = 1;
		else
			gameLevel++;


		for(MPPlayer p : players.values())
		{
			if(p.coordinate != null){
				System.out.println("Atstatau gyvyvbes");
				p.health = p.baseHealth;
				p.speed = p.baseSpeed;

			}
		}



		ChangeLevel(gameLevel);

	}
	public void stopPlayers()
	{
		for(MPPlayer p : players.values())
		{
			if(p.coordinate != null){
				p.isHoldingDown = false;
				p.isHoldingUp = false;
				p.isHoldingLeft = false;
				p.isHoldingRight = false;
				p.isHoldingSkill = false;
				p.isHoldingUse = false;
			}


		}	
	}

	public void ChangeLevel(int level){

		levelChangeCounter = 60 * 2;

		stopPlayers();
		gameLevel = level;

		IStageBuilder builder;
		AbstractFactory factory;
		switch (level){
			case 1:
			default:
				builder = new Stage1Builder(20);
				factory = new Stage1Factory();
				this.gameBoard = new GameBoard(factory, builder, 1);
				break;
			case 2:
				builder = new Stage2Builder(20);
				factory = new Stage1Factory();
				this.gameBoard = new GameBoard(factory, builder, 2);
				break;
			case 3:
				builder = new Stage3Builder(20);
				factory = new Stage1Factory();
				this.gameBoard = new GameBoard(factory, builder, 3);
				break;
		}
		respawnAllPlayers();
		refreshMapForAllPlayers();



	}
	
	public static GameServer getInstance()
	{
		if (gameServer == null)
		{
			gameServer = new GameServer();
		}
		return gameServer;
	}
	
	private boolean initConnection()
	{
		this.network = new Network();
		
		if (!this.network.initKryoServer())
		{
			return false;
		}

		return true;
	}

	public void respawnAllPlayers()
	{
		for(MPPlayer p : players.values())
		{
			if(p.coordinate != null){
				this.respawnPlayer(p);
			}


		}
		
	}
	
	public void respawnPlayer(PlayerInfo p)
	{
		GameServer gameserver = GameServer.getInstance();
		boolean teleported = false;
		int maxRetry = 60;
		int retry = 0;
		while (!teleported && retry < maxRetry)
		{
			int randomCoordX = getRandomNumber (0, gameserver.gameBoard.gridSize);
			int randomCoordY = getRandomNumber (0, gameserver.gameBoard.gridSize);
			if (gameserver.gameBoard.objects[randomCoordX][randomCoordY] instanceof Ground)
			{
				p.coordinate.x = randomCoordX * (gameserver.gameBoard.size / gameserver.gameBoard.gridSize);
				p.coordinate.y = randomCoordY * (gameserver.gameBoard.size / gameserver.gameBoard.gridSize);
				teleported = true;
			}
			retry ++;
		} 
	}
	
	private int getRandomNumber(int min, int max) 
	{
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	public void updatePlayer(int id, PacketUpdatePlayerPos playerPacket) 
	{
		MPPlayer player = players.get(id);
		if (player != null && levelChangeCounter < 0)
		{
			player.isHoldingUp = playerPacket.isHoldingUp != null ? playerPacket.isHoldingUp : player.isHoldingUp;
			player.isHoldingDown = playerPacket.isHoldingDown != null ? playerPacket.isHoldingDown : player.isHoldingDown;
			player.isHoldingLeft = playerPacket.isHoldingLeft != null ? playerPacket.isHoldingLeft : player.isHoldingLeft;
			player.isHoldingRight = playerPacket.isHoldingRight != null ? playerPacket.isHoldingRight : player.isHoldingRight;
			player.isHoldingUse = playerPacket.isHoldingUse != null ? playerPacket.isHoldingUse : player.isHoldingUse;
			player.isHoldingSkill = playerPacket.isHoldingSkill != null ? playerPacket.isHoldingSkill : player.isHoldingSkill;
			players.put(player.id, player);
		}
	}

	public void addPlayer(MPPlayer player)
	{
		this.players.put(player.c.getID(), player);
		this.respawnPlayer(player);
		this.network.sendGameBoard(gameBoard, player);
		
	}
	
	public void refreshMapForAllPlayers()
	{
		for(MPPlayer p : players.values())
		{
			network.sendPlayerInfo(p, true);
			this.network.sendGameBoard(gameBoard, p);
		}
	}
	
	public void removePlayer(int id) 
	{
		
		players.remove(id);
	}
	
    
    private class GameCycleThread extends Thread
    {
    	volatile boolean isGameRunning = true;
    	private final int gameSpeed = 16; //The lower the number the faster the game is


    	
    	public GameCycleThread()
    	{
    		this.isGameRunning = true;
    	}
    	
        public void run()
        {
            long lastTime = System.nanoTime();
            final double ns = 1000000000.0 / 60.0;
            double delta = 0;
            while(true){
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while(delta >= 1){
    				update();
                    delta--;
                    }
                }
        }
        
        public void stopGame()
        {
        	this.isGameRunning = false;
        	
        	//network should probably be closed by the parent
        	network.close();
        }
        
        private void update()
        {

			counter++;
			levelChangeCounter--;
			currentState.handleUpdate();
			if (currentState.isReadyForNextStage())
			{
				currentState.getNextState(getContext());
			}
			
			if (enableMovement)
			{
	        	updatePlayers();
	        	gameBoard.runTick();
			}
			surenkamTeksta();


			checkIfPlayerDead();




        }



		private void surenkamTeksta(){
			InputStreamReader fileInputStream=new InputStreamReader(System.in);
			BufferedReader bufferedReader=new BufferedReader(fileInputStream);

			String tekstas = "nieko nenuskaityta";


			try {
				if (bufferedReader.ready()){

					tekstas = bufferedReader.readLine();



					System.out.println("irasytas tekstas " + tekstas);



					String tekstasLower = tekstas.toLowerCase();


					String[] sarasas = tekstasLower.split(" ");


					if(sarasas.length == 1){

						items.getItemList();

					}
					else if(sarasas.length > 3){
						ConversionContext task = new ConversionContext(tekstas);

						String whatToPlace = task.getWhat();
						String whereToPlace = task.getWhere();
						int howManyToPlace = task.getQuantity();


						Task newTask = new Task(howManyToPlace, whatToPlace, whereToPlace, gameBoard);

						gameBoard = chain1.result(newTask);

						System.out.println(whatToPlace + " How many: " + howManyToPlace);
					} else if(sarasas.length > 1){

						if(sarasas[0].equals("change") && sarasas[1].equals("level")){
							int lygis;
							try {
								lygis = Integer.parseInt(sarasas[2]);

								if(lygis > 0 && lygis < 4){
									ChangeLevel(lygis);

								}else {
									System.out.println("Toks lygis neegzistuoja: " + lygis);
								}

							} catch (NumberFormatException e) {
								System.out.println("Toks lygis neegzistuoja: " + sarasas[2]);
							}
						}
						else {
							System.out.println("Unknown command " + tekstas);
						}


					} else
					{
						System.out.println("Unknown command " + tekstas);
					}



				}


			} catch (IOException e) {
				e.printStackTrace();
			}









		}

        
        private void updatePlayers()
        {
        	int k = 0;
    		for(MPPlayer p : players.values())
    		{
    			if (k!= 0)
    			{
	    			if (p.isHoldingSkill)
	    			{
	    				p.tryUsingSpell();
	    			}

	    			p.onTick();
					p.reduceTimer();

					if (p.isHoldingUse)
					{

						if(p.bombTimer < 0){
							gameBoard.SpawnBomb(p);

							p.setBombTimer(2);
						}

					}
					p.coordinate = checkCollision(p);
					network.sendGameBoard(gameBoard, p);
	    			network.sendPlayerInfo(p, true);
    			}
    			k++;
    		}
        }
        
        private Vector2f checkCollision(MPPlayer p)
        {
        	
        	Vector2f coordsAfterMove =  new Vector2f(p.coordinate.x, p.coordinate.y);
			
        	float padding = 0.001f;
			float cellSize = gameBoard.cellSize();
			
			boolean moveX = true;
			boolean moveY = true;
			
			if (p.isHoldingLeft)
			{
				coordsAfterMove.x -= p.speed; 
			}
			
			if (p.isHoldingRight)
			{
				
				coordsAfterMove.x += p.speed; 
			}
			
			boolean collidingLeft = ((int)coordsAfterMove.x / cellSize - padding) < ((int)p.coordinate.x / cellSize);
			boolean collidingRight = (((int)coordsAfterMove.x + p.size + padding) / cellSize) > (((int)p.coordinate.x + p.size) /cellSize);
			boolean isCollidingX = collidingLeft || collidingRight;
			moveX = !(coordsAfterMove.x <= 0 || coordsAfterMove.x >= gameBoard.size - p.size);
			//Some smoothing when going around edges would be nice
			if (isCollidingX && moveX)
			{
				int x = 0, y = 0, y1 = 0;
				if (collidingLeft)
				{
					x = (int) ((coordsAfterMove.x) / cellSize);
					y = (int) (p.coordinate.y / cellSize);
					y1 = (int) ((p.coordinate.y + p.size) / cellSize);
					
				}
				if (collidingRight)
				{
					x = (int) ((coordsAfterMove.x + p.size) / cellSize); 
					y = (int) (p.coordinate.y / cellSize);
					y1 = (int) ((p.coordinate.y + p.size) / cellSize);
					
				}
				
				if (y == y1)
				{
					moveX = gameBoard.objects[x][y].isWalkable;
					gameBoard.objects[x][y].onStep(p);
				}
				else
				{
					moveX = gameBoard.objects[x][y].isWalkable && gameBoard.objects[x][y1].isWalkable;
					gameBoard.objects[x][y].onStep(p);
					gameBoard.objects[x][y1].onStep(p);
				}
			}
			
			
			if (p.isHoldingUp)
			{
				coordsAfterMove.y += p.speed; 
			}
			
			if (p.isHoldingDown)
			{
				coordsAfterMove.y -= p.speed; 
			}
			
			boolean collidingUp = (((int)coordsAfterMove.y + p.size - padding) / cellSize) > (((int)p.coordinate.y + p.size) / cellSize);
			boolean collidingDown = ((int)coordsAfterMove.y / cellSize + padding) < ((int)p.coordinate.y / cellSize);
			boolean isCollidingY = collidingUp || collidingDown;
			moveY = !(coordsAfterMove.y <= 0 || coordsAfterMove.y >= gameBoard.size - p.size);
			
			//Some smoothing when going around edges would be nice
			if (isCollidingY && moveY)
			{
				int x = 0, x1 = 0, y = 0;
				
				if (collidingUp)
				{
					y = (int) ((coordsAfterMove.y + p.size) / cellSize); 
					x = (int) (p.coordinate.x / cellSize);
					x1 = (int) ((p.coordinate.x + p.size) / cellSize);
					
				}
				if (collidingDown)
				{
					y = (int) ((coordsAfterMove.y) / cellSize);
					x = (int) (p.coordinate.x / cellSize);
					x1 = (int) ((p.coordinate.x + p.size) / cellSize);
				}
				
				if (x == x1)
				{
					moveY = gameBoard.objects[x][y].isWalkable;
					gameBoard.objects[x][y].onStep(p);
				}
				else
				{
					moveY = gameBoard.objects[x][y].isWalkable && gameBoard.objects[x1][y].isWalkable;
					gameBoard.objects[x][y].onStep(p);
					gameBoard.objects[x1][y].onStep(p);
				}
			}
			
			coordsAfterMove.x = moveX ? coordsAfterMove.x : p.coordinate.x;
			coordsAfterMove.y = moveY ? coordsAfterMove.y : p.coordinate.y;
			
			return coordsAfterMove;
				
        }
        
    }
    
}

