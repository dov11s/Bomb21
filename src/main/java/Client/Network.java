package Client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import shared.PackeUpdatePlayerPos;
import shared.PacketAddPlayer;
import shared.PacketRemovePlayer;
import shared.Player;
import shared.Vector2f;


public class Network extends Listener {

	UpdateGameDataDelegate updateGameDataDelegate;
	Client client;
	String ip = "localhost";
	int port = 27960;
	
	public boolean connect(UpdateGameDataDelegate updateGameDataDelegate)
	{
		this.updateGameDataDelegate = updateGameDataDelegate;
		this.client = new Client();
		this.client.getKryo().register(PackeUpdatePlayerPos.class);
		this.client.getKryo().register(PacketAddPlayer.class);
		this.client.getKryo().register(PacketRemovePlayer.class);
		this.client.getKryo().register(Vector2f.class);
		this.client.addListener(this);
	
		this.client.start();
		try
		{
			client.connect(5000, ip, port, port);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void received(Connection connection, Object object)
	{
		if(object instanceof PacketAddPlayer)
		{
			PacketAddPlayer packet = (PacketAddPlayer) object;
			Player newPlayer = new Player();
			updateGameDataDelegate.addPlayer(packet.id, newPlayer);
			
		}
		else if(object instanceof PacketRemovePlayer)
		{
			PacketRemovePlayer packet = (PacketRemovePlayer) object;
			updateGameDataDelegate.removePlayer(packet.id);
			
		}
		else if(object instanceof PackeUpdatePlayerPos)
		{
			PackeUpdatePlayerPos packet = (PackeUpdatePlayerPos) object;
			if (packet.accepted)
			{
				Player player = new Player(packet.id, packet.coordinate);
				updateGameDataDelegate.updatePlayer(player);
			}
			
		}
	}
	
	public void sendPacketButtonPressLeft()
	{
		PackeUpdatePlayerPos packet = new PackeUpdatePlayerPos();
		packet.coordinate.x = -1;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonPressRight()
	{
		PackeUpdatePlayerPos packet = new PackeUpdatePlayerPos();
		packet.coordinate.x = 1;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonPressUp()
	{
		PackeUpdatePlayerPos packet = new 	PackeUpdatePlayerPos();
		packet.coordinate.y = 1;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonPressDown()
	{
		PackeUpdatePlayerPos packet = new 	PackeUpdatePlayerPos();
		packet.coordinate.y = -1;
		this.client.sendUDP(packet);
	}
}
