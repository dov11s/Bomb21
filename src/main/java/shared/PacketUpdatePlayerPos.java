package shared;

public class PacketUpdatePlayerPos extends Packet
{

	public Vector2f coordinate;
	public Boolean isHoldingLeft;
	public Boolean isHoldingRight;
	public Boolean isHoldingUp;
	public Boolean isHoldingDown;
	public Boolean isHoldingUse;
	
	public PacketUpdatePlayerPos()
	{
		this.coordinate = new Vector2f();
	}
	
	public PacketUpdatePlayerPos(Vector2f coordinate)
	{
		this.coordinate = coordinate;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
	}
	
	public PacketUpdatePlayerPos(int id, Player player)
	{
		this.id = id;
		this.coordinate = player.coordinate;
		this.isHoldingLeft = player.isHoldingLeft;
		this.isHoldingRight = player.isHoldingRight;
		this.isHoldingUp = player.isHoldingUp;
		this.isHoldingDown = player.isHoldingDown;
	}
}
