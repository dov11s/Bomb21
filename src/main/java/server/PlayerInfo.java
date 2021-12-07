package server;

import shared.Vector2f;

import java.util.Random;

public class PlayerInfo 
{
	public int id;
	public Vector2f coordinate;
	public boolean isHoldingLeft;
	public boolean isHoldingRight;
	public boolean isHoldingUp;
	public boolean isHoldingDown;
	public boolean isHoldingUse;
	public boolean isHoldingSkill;
	public boolean isHoldingPause;
	public boolean isHoldingUnPause;
	public boolean placedBomb = true;
	public int health;
	public float speed;
	public float baseSpeed;
	public int size;
	public int bombCount;

	private SkillAlgorithm skillAlgorithm;
	
	public PlayerInfo()
	{

		Random r = new Random();
		int low = 100;
		int high = 800;



		int x = r.nextInt(high-low) + low;
		int y = r.nextInt(high-low) + low;


		this.coordinate = new Vector2f();
		this.coordinate.x = x;
		this.coordinate.y = y;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.isHoldingPause = false;
		this.isHoldingUnPause = false;
		this.skillAlgorithm = new DashSkill();
		this.size = 40;
		this.speed = 2.5f;
		this.baseSpeed = 2.5f;
		this.health = 1;
		this.bombCount = 2;
		//this.playerStats = new ConcretePlayer();
	}


	public  void ChangePlayerLocation(int x, int y){

		this.coordinate.x = x;
		this.coordinate.y = y;

	}
	
	public PlayerInfo(int id, Vector2f coordinate)
	{
		this.id = id;
		this.coordinate = coordinate;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.isHoldingUse = false;
		this.isHoldingPause = false;
		this.isHoldingUnPause = false;
		this.size = 40;
		this.speed = 2.5f;
		this.baseSpeed = 2.5f;
		this.health = 3;
		this.bombCount = 2;
		//Test
	}
	
	public SkillAlgorithm getSkillAlgorithm() 
	{
		return skillAlgorithm;
	}

	public void setSkillAlgorithm(SkillAlgorithm skillAlgorithm) 
	{
		this.skillAlgorithm = skillAlgorithm;
	}

	
	public void onTick()
	{
		this.skillAlgorithm.onTick(this);
	}
	
	public void tryUsingSpell()
	{
		this.skillAlgorithm.useSkill(this);
	}
	
	public int getCooldown()
	{
		return this.skillAlgorithm.getCooldown();
	}
	
	public String getName()
	{
		return this.skillAlgorithm.getName();
	}
}
