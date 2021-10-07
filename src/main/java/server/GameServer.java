package server;

import java.util.HashMap;
import java.util.Map;

import shared.Vector2f;
import shared.PacketUpdatePlayerPos;

public class GameServer implements UpdateGameDataDelegate
{
	protected GameBoard gameBoard;
	protected volatile Map<Integer, MPPlayer> players;
	protected volatile Network network;
	private GameCycleThread thread;
	private Stage1Factory stage1factory;
	private boolean updateBoard = false;
	
	public GameServer()
	{
		//Init Connection
		if (!initConnection())
		{
			System.err.println("ERROR Connecting to host");
			return;
		}
		
		this.players = new HashMap<Integer, MPPlayer>();
		this.stage1factory = new Stage1Factory();
		this.gameBoard = new GameBoard(stage1factory);
		
		this.thread = new GameCycleThread();
		this.thread.start();
	}
	
	private boolean initConnection()
	{
		this.network = new Network();
		
		if (!this.network.initKryoServer(this))
		{
			return false;
		}

		return true;
	}

	@Override
	public void updatePlayer(int id, PacketUpdatePlayerPos playerPacket) 
	{
		MPPlayer player = players.get(id);
		if (player != null) 
		{
			player.isHoldingUp = playerPacket.isHoldingUp != null ? playerPacket.isHoldingUp : player.isHoldingUp;
			player.isHoldingDown = playerPacket.isHoldingDown != null ? playerPacket.isHoldingDown : player.isHoldingDown;
			player.isHoldingLeft = playerPacket.isHoldingLeft != null ? playerPacket.isHoldingLeft : player.isHoldingLeft;
			player.isHoldingRight = playerPacket.isHoldingRight != null ? playerPacket.isHoldingRight : player.isHoldingRight;
			
			players.put(player.id, player);
		}
	}


	@Override
	public void addPlayer(MPPlayer player)
	{
		this.players.put(player.c.getID(), player);
		this.network.sendGameBoard(gameBoard, player);
		
	}


	@Override
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
        	while (this.isGameRunning)
        	{    	
        		try 
        		{
        			//Probably should use Timer instead
        			Thread.sleep(gameSpeed);
    				this.update();
    			} 
        		catch (InterruptedException e)
        		{
    				e.printStackTrace();
    				this.stopGame();
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
        	updatePlayers();
        	gameBoard.runTick();
        }
        
        private void updatePlayers()
        {
    		for(MPPlayer p : players.values())
    		{
    			p.coordinate = checkCollision(p);
    			network.sendPlayerInfo(p, true);
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

