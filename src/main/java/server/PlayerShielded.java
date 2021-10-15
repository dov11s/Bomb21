package server;

public class PlayerShielded extends PlayerDecorator
{
	boolean isActive;
	public PlayerShielded(PlayerStats newPlayerstats)
	{
		super(newPlayerstats);
		isActive = true;
	}
	
	public void takeDamage()
	{
		if (isActive)
		{
			isActive = false;
			return;
		}
		else
		{
			this.playerStats.takeDamage();
		}
	}
	
	public String getAplifierList() 
	{
		if (isActive)
		{
			return playerStats.getAplifierList() + ", PlayerShielded";
		}
		else
		{
			return playerStats.getAplifierList();
		}
	}

}
