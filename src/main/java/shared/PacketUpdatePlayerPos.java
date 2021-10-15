package shared;

import server.PlayerInfo;

public class PacketUpdatePlayerPos extends Packet
{

	public Vector2f coordinate;
	public Boolean isHoldingLeft;
	public Boolean isHoldingRight;
	public Boolean isHoldingUp;
	public Boolean isHoldingDown;
	public int size;
	public Boolean isHoldingUse;
	
	public PacketUpdatePlayerPos()
	{
		this.coordinate = new Vector2f();
	}
	
	public PacketUpdatePlayerPos(Vector2f coordinate, int size)
	{
		this.coordinate = coordinate;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.isHoldingUse = false;
	}
	
	public PacketUpdatePlayerPos(int id, PlayerInfo player)
	{
		this.id = id;
		this.coordinate = player.coordinate;
		this.isHoldingLeft = player.isHoldingLeft;
		this.isHoldingRight = player.isHoldingRight;
		this.isHoldingUp = player.isHoldingUp;
		this.isHoldingDown = player.isHoldingDown;
		this.isHoldingUse = player.isHoldingUse;
		this.size = player.playerStats.getSize();
	}
}
