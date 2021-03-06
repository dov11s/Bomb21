package client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import shared.PacketUpdatePlayerPos;
import shared.ObjectType;
import shared.PacketAddPlayer;
import shared.PacketRemovePlayer;
import shared.PacketSendString;
import shared.PacketUpdateGameBoard;
import shared.SimplifiedGameBoard;
import shared.SimplifiedGameObject;
import shared.SimplifiedPlayer;
import shared.Vector2f;


public class Network extends Listener 
{

	UpdateGameDataDelegate updateGameDataDelegate;
	Client client;
	//String ip = "158.129.18.31";
	String ip = "localhost";
	int port = 27960;
	
	public boolean connect(UpdateGameDataDelegate updateGameDataDelegate)
	{
		this.updateGameDataDelegate = updateGameDataDelegate;
		this.client = new Client(65536, 16384);
		this.client.getKryo().register(PacketUpdatePlayerPos.class);
		this.client.getKryo().register(PacketAddPlayer.class);
		this.client.getKryo().register(PacketRemovePlayer.class);
		this.client.getKryo().register(PacketUpdateGameBoard.class);
		this.client.getKryo().register(SimplifiedGameBoard.class);
		this.client.getKryo().register(ObjectType.class);	
		this.client.getKryo().register(SimplifiedGameObject.class);
		this.client.getKryo().register(SimplifiedGameObject[].class);
		this.client.getKryo().register(SimplifiedGameObject[][].class);
		this.client.getKryo().register(Vector2f.class);
		this.client.getKryo().register(PacketSendString.class);
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
			SimplifiedPlayer newPlayer = new SimplifiedPlayer();
			updateGameDataDelegate.addPlayer(packet.id, newPlayer);
			
		}
		else if(object instanceof PacketRemovePlayer)
		{
			PacketRemovePlayer packet = (PacketRemovePlayer) object;
			updateGameDataDelegate.removePlayer(packet.id);
			
		}
		else if(object instanceof PacketUpdatePlayerPos)
		{
			PacketUpdatePlayerPos packet = (PacketUpdatePlayerPos) object;
			if (packet.accepted)
			{
				SimplifiedPlayer player = new SimplifiedPlayer(packet);
				updateGameDataDelegate.updatePlayer(player);
			}
			
		}
		else if (object instanceof PacketUpdateGameBoard)
		{
			PacketUpdateGameBoard packet = (PacketUpdateGameBoard) object;
			updateGameDataDelegate.updateBoard(packet.gameBoard);
		}
		else if (object instanceof PacketSendString)
		{
			PacketSendString packet = (PacketSendString) object;
			updateGameDataDelegate.updateDisplayString(packet.text);
			
		}
	}
	
	public void sendPacketButtonPressLeft()
	{
		PacketUpdatePlayerPos packet = new PacketUpdatePlayerPos();
		packet.isHoldingLeft = true;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonPressRight()
	{
		PacketUpdatePlayerPos packet = new PacketUpdatePlayerPos();
		packet.isHoldingRight = true;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonPressUp()
	{
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingUp = true;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonPressDown()
	{
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingDown = true;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonReleaseLeft()
	{
		PacketUpdatePlayerPos packet = new PacketUpdatePlayerPos();
		packet.isHoldingLeft = false;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonReleaseRight()
	{
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingRight = false;
		this.client.sendUDP(packet);
	}

	public void sendPacketButtonReleaseUp()
	{
		PacketUpdatePlayerPos packet = new PacketUpdatePlayerPos();
		packet.isHoldingUp = false;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonReleaseDown()
	{
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingDown = false;
		this.client.sendUDP(packet);
	}

	public void sendPacketButtonPressUse() {
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingUse = true;
		this.client.sendUDP(packet);
	}

	public void sendPacketButtonReleaseUse() {
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingUse = false;
		this.client.sendUDP(packet);
		
	}
	
	public void sendPacketButtonPressSkill() 
	{
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingSkill = true;
		this.client.sendUDP(packet);
	}
	
	public void sendPacketButtonReleaseSkill() {
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingSkill = false;
		this.client.sendUDP(packet);
		
	}

	public void sendPacketButtonPressPause()
	{
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingSkill = true;
		this.client.sendUDP(packet);
	}

	public void sendPacketButtonReleasePause() {
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingSkill = false;
		this.client.sendUDP(packet);

	}

	public void sendPacketButtonPressUnPause()
	{
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingSkill = true;
		this.client.sendUDP(packet);
	}

	public void sendPacketButtonReleaseUnPause() {
		PacketUpdatePlayerPos packet = new 	PacketUpdatePlayerPos();
		packet.isHoldingSkill = false;
		this.client.sendUDP(packet);

	}
	
}
