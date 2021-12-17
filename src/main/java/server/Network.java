package server;

import org.lwjgl.system.CallbackI.P;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import shared.PacketUpdatePlayerPos;
import shared.SimplifiedGameBoard;
import shared.SimplifiedGameObject;
import shared.ObjectType;
import shared.PacketAddPlayer;
import shared.PacketRemovePlayer;
import shared.PacketSendString;
import shared.PacketUpdateGameBoard;
import shared.Vector2f;

public class Network extends Listener
{
	private Server server;
	private final int port = 27960;
	
	public boolean initKryoServer()
	{
		try 
		{
			this.server = new Server(131072, 16384);
			this.server.getKryo().register(PacketUpdatePlayerPos.class);
			this.server.getKryo().register(PacketAddPlayer.class);
			this.server.getKryo().register(PacketRemovePlayer.class);
			this.server.getKryo().register(PacketUpdateGameBoard.class);
			this.server.getKryo().register(SimplifiedGameBoard.class);
			this.server.getKryo().register(ObjectType.class);	
			this.server.getKryo().register(SimplifiedGameObject.class);
			this.server.getKryo().register(SimplifiedGameObject[].class);
			this.server.getKryo().register(SimplifiedGameObject[][].class);
			this.server.getKryo().register(Vector2f.class);
			this.server.getKryo().register(PacketSendString.class);
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
		GameServer.getInstance().addPlayer(player);
		System.out.println("Connection received.");
	}
	
	public void sendPlayerInfo(MPPlayer player, boolean isAccepted)
	{
		PacketUpdatePlayerPos packet = player.getPacketUpdatePlayerPos(player.c.getID());
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
	
	public void sendString(String text)
	{
		PacketSendString packet = new PacketSendString(text);
		this.server.sendToAllUDP(packet);
	}
	
	public void received(Connection connection, Object object)
	{
		if(object instanceof PacketUpdatePlayerPos)
		{
			PacketUpdatePlayerPos packet = (PacketUpdatePlayerPos) object;
			GameServer.getInstance().updatePlayer(connection.getID(), packet);
			System.out.println("Received coordinate packet");
		}
	}
	
	public void disconnected(Connection c)
	{
		PacketRemovePlayer packet = new PacketRemovePlayer();
		packet.id = c.getID();
		this.server.sendToAllExceptTCP(c.getID(), packet);
		GameServer.getInstance().removePlayer(c.getID());
		System.out.println("Connection dropped.");
	}
}

