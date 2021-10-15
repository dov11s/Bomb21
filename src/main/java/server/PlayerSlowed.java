package server;

public class PlayerSlowed extends PlayerDecorator 
{
	int currentSlowTime;
	final int maxSlow = 120; //Two seconds

	public PlayerSlowed(PlayerStats newPlayerstats)
	{
		super(newPlayerstats);
		currentSlowTime = 0;
	}
	
	public float getSpeed() 
	{
		currentSlowTime ++;
		if (currentSlowTime < maxSlow)
		{
			return playerStats.getSpeed() * 0.5f;
		}
		else
		{
			return playerStats.getSpeed();
		}
	}
	
	public String getAplifierList() 
	{
		if (currentSlowTime < maxSlow)
		{
			return playerStats.getAplifierList() + ", PlayerSlowed";
		}
		else
		{
			return playerStats.getAplifierList();
		}
	}

}
