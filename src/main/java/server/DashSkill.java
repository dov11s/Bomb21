package server;

public class DashSkill implements SkillAlgorithm 
{

	private final int cooldown = 10 * 60; //10 seconds
	private final String name = "Teleport";
	private int currentCooldown = 0;
	private final int timer = 1 * 60;
	private int currentTimer = 0;
	
	@Override
	public void useSkill(PlayerInfo p) 
	{
		if (this.currentCooldown == 0)
		{
			this.currentTimer = timer;
			this.currentCooldown = this.cooldown;
		}
	}

	@Override
	public void onTick(PlayerInfo p)
	{
		if (this.currentCooldown > 0)
		{
			this.currentCooldown --;
		}
		
		if (this.currentTimer > 0)
		{
			//TODO do something
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
