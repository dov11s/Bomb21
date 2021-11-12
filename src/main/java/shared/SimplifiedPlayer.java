package shared;

public class SimplifiedPlayer 
{
	public int id;
	public int size;
	public Vector2f coordinate;
	public boolean isHoldingLeft;
	public boolean isHoldingRight;
	public boolean isHoldingUp;
	public boolean isHoldingDown;
	public boolean isHoldingUse;
	public boolean isHoldingSkill;
	public float speed;
	public int health;
	public int skillCooldown;
	public String skillName;
	
	public SimplifiedPlayer() {}
	public SimplifiedPlayer(int id) 
	{
		this.id = id;
		this.coordinate = new Vector2f(256,256);
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.isHoldingUse = false;
		this.health = 3;
		this.skillName = "none";
		this.skillCooldown = 0;
	}
	
	public SimplifiedPlayer(PacketUpdatePlayerPos packet)
	{
		this.id = packet.id;
		this.coordinate = packet.coordinate;
		this.isHoldingLeft = packet.isHoldingLeft;
		this.isHoldingRight = packet.isHoldingRight;
		this.isHoldingUp = packet.isHoldingUp;
		this.isHoldingDown = packet.isHoldingDown;
		this.size = packet.size;
		this.isHoldingUse = packet.isHoldingUse;
		this.isHoldingSkill = packet.isHoldingSkill;
		this.skillName = packet.skillName;
		this.skillCooldown = packet.skillCooldown;
		this.health = packet.health;
	
	}
}
