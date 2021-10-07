package server;

import java.util.HashMap;
import java.util.Map;

import shared.GameBoard;
import shared.GameObject;
import shared.Stage1Factory;
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
        	
			if (p.isHoldingLeft)
			{
				coordsAfterMove.x -= p.speed; 
			}
			
			if (p.isHoldingRight)
			{
				
				coordsAfterMove.x += p.speed; 
			}
			
			if (p.isHoldingUp)
			{
				coordsAfterMove.y += p.speed; 
			}
			
			if (p.isHoldingDown)
			{
				coordsAfterMove.y -= p.speed; 
			}
			
			boolean collidingLeft = ((int)coordsAfterMove.x / gameBoard.gridSize) != ((int)p.coordinate.x / gameBoard.gridSize);
			boolean collidingRight = (((int)coordsAfterMove.x + p.size) / gameBoard.gridSize) != (((int)p.coordinate.x + p.size) / gameBoard.gridSize);
			boolean collidingUp = (((int)coordsAfterMove.y + p.size) / gameBoard.gridSize) != (((int)p.coordinate.y + p.size) / gameBoard.gridSize);
			boolean collidingDown = ((int)coordsAfterMove.x / gameBoard.gridSize) != ((int)p.coordinate.x / gameBoard.gridSize);
			
			boolean isColliding = collidingLeft || collidingRight || collidingUp || collidingDown;
			 
			boolean moveX = true;
			boolean moveY = true;
			
			//Some smoothing when going around edges would be nice
			if (isColliding)
			{
				int x, x1;
				int y, y1;
				
				if (collidingLeft)
				{
					x = ((int)coordsAfterMove.x) / gameBoard.gridSize;
					y = (int)coordsAfterMove.y / gameBoard.gridSize;
					y1 = ((int)coordsAfterMove.y + p.size) / gameBoard.gridSize;
					
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
				else if (collidingRight)
				{
					x = ((int)coordsAfterMove.x + p.size) / gameBoard.gridSize; 
					y = (int)coordsAfterMove.y / gameBoard.gridSize;
					y1 = ((int)coordsAfterMove.y + p.size) / gameBoard.gridSize;
					
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
				else if (collidingUp)
				{
					y = ((int)coordsAfterMove.y + p.size) / gameBoard.gridSize; 
					x = (int)coordsAfterMove.x / gameBoard.gridSize;
					x1 = ((int)coordsAfterMove.x + p.size) / gameBoard.gridSize;
					
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
				else if (collidingDown)
				{
					y = ((int)coordsAfterMove.x) / gameBoard.gridSize;
					x = (int)coordsAfterMove.y / gameBoard.gridSize;
					x1 = ((int)coordsAfterMove.y + p.size) / gameBoard.gridSize;
					
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
			}
			
			coordsAfterMove.x = moveX ? coordsAfterMove.x : p.coordinate.x;
			coordsAfterMove.y = moveY ? coordsAfterMove.y : p.coordinate.y;
			
			return coordsAfterMove;
				
        }
        
    }
    
}

