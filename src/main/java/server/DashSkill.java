package server;

import shared.SimplifiedPlayer;

public class DashSkill implements SkillAlgorithm 
{

	private final int cooldown = 10 * 60; //10 seconds
	private final String name = "Dash";
	private int currentCooldown = 0;
	private final int timer = 1 * 30; //0.25 second
	private int currentTimer = 0;
	private SimplifiedPlayer simplified; //For direction
	private float previousSpeed = 0;
	@Override
	public void useSkill(PlayerInfo p) 
	{
		if (this.currentCooldown == 0)
		{
			GameServer.getInstance().dash.increaseCount();
			this.currentTimer = timer;
			this.currentCooldown = this.cooldown;
			this.previousSpeed = p.speed;
			p.speed += 10;
			this.simplified = new SimplifiedPlayer();
			this.simplified.isHoldingDown = p.isHoldingDown;
			this.simplified.isHoldingUp = p.isHoldingUp;
			this.simplified.isHoldingLeft = p.isHoldingLeft;
			this.simplified.isHoldingRight = p.isHoldingRight;
		}
	}

	@Override
	public void onTick(PlayerInfo p)
	{
		if (this.currentCooldown > 0)
		{
			this.currentCooldown --;
		}
		
		if (this.currentTimer > 1)
		{
			p.isHoldingDown = this.simplified.isHoldingDown;
			p.isHoldingUp = this.simplified.isHoldingUp;
			p.isHoldingLeft = this.simplified.isHoldingLeft;
			p.isHoldingRight = this.simplified.isHoldingRight;
			this.currentTimer --;
		}
		else if (this.currentTimer == 1)
		{
			p.speed = this.previousSpeed;
			this.currentTimer --;
		}
			
	}

	@Override
	public String getName() 
	{
		return this.name;
	}

	@Override
	public int getCooldown() 
	{
		return this.currentCooldown;
	}
}
