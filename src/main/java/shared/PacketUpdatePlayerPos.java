package shared;

public class PacketUpdatePlayerPos extends Packet
{

	public Vector2f coordinate;
	public Boolean isHoldingLeft;
	public Boolean isHoldingRight;
	public Boolean isHoldingUp;
	public Boolean isHoldingDown;
	public int size;
	public Boolean isHoldingUse;
	public Boolean isHoldingSkill;
	public boolean isHoldingPause;
	public boolean isHoldingUnPause;
	public int skillCooldown;
	public String skillName;
	public int health;
	
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
		this.isHoldingSkill = false;
		this.isHoldingPause = false;
		this.isHoldingUnPause = false;
	}
}
