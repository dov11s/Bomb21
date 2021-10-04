package shared;

public class Player 
{
	public int id;
	public Vector2f coordinate;
	public float speed;
	public boolean isHoldingLeft;
	public boolean isHoldingRight;
	public boolean isHoldingUp;
	public boolean isHoldingDown;
	
	public Player()
	{
		this.coordinate = new Vector2f();
		this.coordinate.x = 256;
		this.coordinate.y = 256;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.speed = 1f;
	}
	
	public Player(int id, Vector2f coordinate)
	{
		this.id = id;
		this.coordinate = coordinate;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
	}
	
	public Player(PacketUpdatePlayerPos packet)
	{
		this.id = packet.id;
		this.coordinate = packet.coordinate;
		this.isHoldingLeft = packet.isHoldingLeft;
		this.isHoldingRight = packet.isHoldingRight;
		this.isHoldingUp = packet.isHoldingUp;
		this.isHoldingDown = packet.isHoldingDown;
	}
}
