package server;

import shared.Vector2f;

public class PlayerInfo 
{
	public int id;
	public Vector2f coordinate;
	public PlayerStats playerStats;
	public boolean isHoldingLeft;
	public boolean isHoldingRight;
	public boolean isHoldingUp;
	public boolean isHoldingDown;
	public boolean isHoldingUse;
	public boolean placedBomb = true;
	public UsableSkill usableSkill;
	
	public PlayerInfo()
	{
		this.playerStats = new ConcretePlayer();
		this.coordinate = new Vector2f();
		this.coordinate.x = 400;
		this.coordinate.y = 400;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.usableSkill = new UsableSkill(this.id);
		//tets
		this.playerStats = new PlayerSlowed(new ConcretePlayer());
		//this.playerStats = new ConcretePlayer();
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
		//Test
		this.playerStats = new PlayerSlowed(new ConcretePlayer());
//		this.playerStats = new ConcretePlayer();
	}
	
	public void tryUsingSpell()
	{
		usableSkill.usableSkill.useSkill();
	}
}
