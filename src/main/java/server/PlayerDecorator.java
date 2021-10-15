package server;

public abstract class PlayerDecorator implements PlayerStats
{
	protected PlayerStats playerStats;
	
	public PlayerDecorator(PlayerStats newPlayerstats)
	{
		this.playerStats = newPlayerstats;
	}

	@Override
	public float getSpeed() 
	{
		return this.playerStats.getSpeed();
	}

	@Override
	public void takeDamage() 
	{
		this.playerStats.takeDamage();
	}

	@Override
	public String getAplifierList() 
	{
		return this.playerStats.getAplifierList();
	}

	@Override
	public int getSize() 
	{
		return this.playerStats.getSize();
	}

	@Override
	public int getBombRadius() 
	{
		return this.playerStats.getBombRadius();
	}

	@Override
	public int getBombCount()
	{
		return this.playerStats.getBombCount();
	}
}
