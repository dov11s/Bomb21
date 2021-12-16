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
	public int baseHealth;
	public float speed;
	public float baseSpeed;
	public int size;
	public int bombCount;
	public int bombTimer;

	public int deathCounter;

	private SkillAlgorithm skillAlgorithm;
	
	public PlayerInfo()
	{

		deathCounter = 0;

		Random r = new Random();
		int low = 100;
		int high = 800;

		setBombTimer(1);



		int x = r.nextInt(high-low) + low;
		int y = r.nextInt(high-low) + low;

		this.baseHealth = 2;
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
		this.health = 2;
		this.bombCount = 2;
		//this.playerStats = new ConcretePlayer();
	}

	public void setBombTimer(int time){
		bombTimer = 30*time;
	}


	public void reduceTimer(){
		bombTimer--;
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
