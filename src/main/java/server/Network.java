package server;

import org.lwjgl.system.CallbackI.P;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import shared.PacketUpdatePlayerPos;
import shared.SimplifiedGameBoard;
import shared.SimplifiedGameObject;
import shared.Stage1Factory;
import shared.GameBoard;
import shared.GameObject;
import shared.Ground;
import shared.PacketAddPlayer;
import shared.PacketRemovePlayer;
import shared.PacketUpdateGameBoard;
import shared.Vector2f;
import shared.Wall;

public class Network extends Listener
{
	private Server server;
	private final int port = 27960;
	UpdateGameDataDelegate updateGameDataDelegate;
	
	public boolean initKryoServer(UpdateGameDataDelegate updateGameDataDelegate)
	{
		try 
		{
			this.updateGameDataDelegate = updateGameDataDelegate;
			this.server = new Server();
			this.server.getKryo().register(PacketUpdatePlayerPos.class);
			this.server.getKryo().register(PacketAddPlayer.class);
			this.server.getKryo().register(PacketRemovePlayer.class);
			this.server.getKryo().register(PacketUpdateGameBoard.class);
			this.server.getKryo().register(SimplifiedGameBoard.class);
			this.server.getKryo().register(SimplifiedGameObject.class);
			this.server.getKryo().register(SimplifiedGameObject[].class);
			this.server.getKryo().register(SimplifiedGameObject[][].class);
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
	
	public void close()
	{
		this.server.close();
	}
	
	public void connected(Connection c)
	{
		MPPlayer player = new MPPlayer();
		player.c = c;
	
		PacketAddPlayer packet = new PacketAddPlayer();
		packet.id = c.getID();
		this.server.sendToAllExceptTCP(c.getID(), packet);
		this.updateGameDataDelegate.addPlayer(player);
		System.out.println("Connection received.");
	}
	
	public void sendPlayerInfo(MPPlayer player, boolean isAccepted)
	{
		PacketUpdatePlayerPos packet = new PacketUpdatePlayerPos(player.c.getID(), player);
		packet.accepted = isAccepted;
		this.server.sendToAllUDP(packet);
		
	}
	
	public void sendGameBoard(GameBoard gameBoard, MPPlayer player)
	{
		SimplifiedGameBoard simpleGameboard = gameBoard.getSimpleGameBoard();
		if (player == null)
		{    
			PacketUpdateGameBoard packet = new PacketUpdateGameBoard(simpleGameboard);
			this.server.sendToAllUDP(packet);
		}
		else
		{
			PacketUpdateGameBoard packet = new PacketUpdateGameBoard(simpleGameboard);
			this.server.sendToUDP(player.c.getID(), packet);
		}
	}
	
	public void received(Connection connection, Object object)
	{
		if(object instanceof PacketUpdatePlayerPos)
		{
			PacketUpdatePlayerPos packet = (PacketUpdatePlayerPos) object;
			this.updateGameDataDelegate.updatePlayer(connection.getID(), packet);
			System.out.println("Received coordinate packet");
		}
	}
	
	public void disconnected(Connection c)
	{
		PacketRemovePlayer packet = new PacketRemovePlayer();
		packet.id = c.getID();
		this.server.sendToAllExceptTCP(c.getID(), packet);
		this.updateGameDataDelegate.removePlayer(c.getID());
		System.out.println("Connection dropped.");
	}
}

