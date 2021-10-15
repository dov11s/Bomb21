package server;

public class PlayerUpgraded extends PlayerDecorator
{
	public PlayerUpgraded(PlayerStats newPlayerstats)
	{
		super(newPlayerstats);
	}
	
	public float getSpeed() 
	{
		return playerStats.getSpeed() + 1f;
	}
	
	public int getSize() 
	{
		return (int) (playerStats.getSize() * 0.9);
	}
	
	public String getAplifierList() 
	{
		return playerStats.getAplifierList() + ", Player upgraded bombs";
	}

}
