package server;

public class NoSkill implements SkillAlgorithm 
{
	private final String name = "None";
	
	@Override
	public void useSkill(PlayerInfo p) 
	{
	}

	@Override
	public void onTick(PlayerInfo p)
	{
	}

	@Override
	public String getName() 
	{
		return this.name;
	}

	@Override
	public int getCooldown() 
	{
		return 0;
	}

}
