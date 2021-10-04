package Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import shared.PackeUpdatePlayerPos;
import shared.PacketAddPlayer;
import shared.PacketRemovePlayer;
import shared.Vector2f;

public class KryoServer extends Listener
{
	private Server server;
	private final int port = 27960;
	private Map<Integer, MPPlayer> players = new HashMap<Integer, MPPlayer>();
	private float playerSpeed = 0.1f;
	
	public KryoServer()
	{
	}
	
	public boolean initServer()
	{
		try 
		{
			this.server = new Server();
			this.server.getKryo().register(PackeUpdatePlayerPos.class);
			this.server.getKryo().register(PacketAddPlayer.class);
			this.server.getKryo().register(PacketRemovePlayer.class);
			this.server.getKryo().register(Vector2f.class);
			this.server.bind(this.port, this.port);
			this.server.addListener(this);
			this.server.start();
			System.out.println("The server is ready");
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;	
		}
	}
	
	public void connected(Connection c)
	{
		MPPlayer player = new MPPlayer();
		player.c = c;
		
		PacketAddPlayer packet = new PacketAddPlayer();
		packet.id = c.getID();
		this.server.sendToAllExceptTCP(c.getID(), packet);
		
		for(MPPlayer p : players.values())
		{
			PacketAddPlayer packet2 = new PacketAddPlayer();
			packet2.id = p.c.getID();
			c.sendTCP(packet2);
		}
		
		players.put(c.getID(), player);
		System.out.println("Connection received.");
	}
	
	public void received(Connection connection, Object object)
	{
		if(object instanceof PackeUpdatePlayerPos)
		{
			PackeUpdatePlayerPos packet = (PackeUpdatePlayerPos) object;
			MPPlayer player = players.get(connection.getID());
			//TODO add timer so player speed does not increase depending on packages sent and clean up the logic.
			//TODO add logic of the game with a grid.. instead of random coordinates
			
			packet.accepted = false;
			
			if (packet == null || packet.coordinate == null)
			{
				//malformed packet
				return;
			}
			
			if (packet.coordinate.x == -1 || packet.coordinate.x == 1)
			{
				player.coordinate.x += packet.coordinate.x * playerSpeed;
				packet.coordinate = player.coordinate;
				packet.accepted = true;
			}
			
			if (packet.coordinate.y == -1 || packet.coordinate.y == 1)
			{
				player.coordinate.y += packet.coordinate.y * playerSpeed;
				packet.coordinate = player.coordinate;
				packet.accepted = true;
			}
			
			packet.id = connection.getID();
			
			//Currently sending player data to everyone when something changes
			this.server.sendToAllTCP(packet);
			System.out.println("received and sent an update coordinate packet");
			
		}
	}
	
	public void disconnected(Connection c)
	{
		players.remove(c.getID());
		PacketRemovePlayer packet = new PacketRemovePlayer();
		packet.id = c.getID();
		this.server.sendToAllExceptTCP(c.getID(), packet);
		System.out.println("Connection dropped.");
	}
}
