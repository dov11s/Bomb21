package server;

import java.util.HashMap;
import java.util.Map;
import shared.PacketUpdatePlayerPos;

public class GameServer implements UpdateGameDataDelegate
{
	
	protected volatile Map<Integer, MPPlayer> players;
	protected volatile Network network;
	private GameCycleThread thread;
	
	public GameServer()
	{
		//Init Connection
		if (!initConnection())
		{
			System.err.println("ERROR Connecting to host");
			return;
		}
		
		this.players = new HashMap<Integer, MPPlayer>();
		
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
    		for(MPPlayer p : players.values())
    		{
    			
    			if (p.isHoldingLeft)
    			{
    				p.coordinate.x -= p.speed; 
    			}
    			
    			if (p.isHoldingRight)
    			{
    				p.coordinate.x += p.speed; 
    			}
    			
    			if (p.isHoldingUp)
    			{
    				p.coordinate.y += p.speed; 
    			}
    			
    			if (p.isHoldingDown)
    			{
    				p.coordinate.y -= p.speed; 
    			}
    			
    			network.sendPlayerInfo(p, true);
    		}
        }
        
    }
}

