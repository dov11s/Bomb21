package server;

public class JumpSkill implements SkillAlgorithm
{
	private final int cooldown = 10 * 60; //10 seconds
	private final String name = "Jump";
	private int currentCooldown = 0;
	
	@Override
	public void useSkill(PlayerInfo p) 
	{
		if (this.currentCooldown == 0)
		{
			//Todo do something
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
