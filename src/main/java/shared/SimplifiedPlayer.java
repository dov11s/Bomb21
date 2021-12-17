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
	public boolean isHoldingPause;
	public boolean isHoldingUnPause;
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
		this.isHoldingPause = false;
		this.isHoldingUnPause = false;
		this.health = 3;
		this.skillName = "none";
		this.skillCooldown = 0;
	}
	public SimplifiedPlayer (SimplifiedPlayer player)
	{
		id = player.id;
		size =  player.size;
		if (player.coordinate == null)
		{
			coordinate = new Vector2f (-200, -200); 
		}
		else
		{
			coordinate =  new Vector2f (player.coordinate.x, player.coordinate.y);
		}
		isHoldingLeft =  player.isHoldingLeft;
		isHoldingRight =  player.isHoldingRight;
		isHoldingUp =  player.isHoldingUp;
		isHoldingDown = player.isHoldingDown;
		isHoldingUse = player.isHoldingUse;
		isHoldingSkill = player.isHoldingSkill;
		isHoldingPause = player.isHoldingPause;
		isHoldingUnPause = player.isHoldingUnPause;
		speed = player.speed;
		health = player.health;
		skillCooldown = player.skillCooldown;
		skillName = player.skillName;
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
		this.isHoldingPause = packet.isHoldingPause;
		this.isHoldingUnPause = packet.isHoldingUnPause;
		this.skillName = packet.skillName;
		this.skillCooldown = packet.skillCooldown;
		this.health = packet.health;
	
	}
}
