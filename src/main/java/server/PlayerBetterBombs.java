package server;

public class PlayerBetterBombs extends PlayerDecorator 
{

	public PlayerBetterBombs(PlayerStats newPlayerstats)
	{
		super(newPlayerstats);
	}
	
	public int getBombRadius() 
	{
		return playerStats.getBombRadius() + 1;
	}
	
	public int getBombCount() 
	{
		return playerStats.getBombCount() + 1;
	}
	
	public String getAplifierList() 
	{
		return playerStats.getAplifierList() + ", Player bigger bombs";
	}

}
