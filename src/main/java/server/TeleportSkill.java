package server;


public class TeleportSkill implements SkillAlgorithm 
{

	private final int cooldown = 10 * 60; //10 seconds
	private final String name = "Teleport";
	private int currentCooldown = 0;
	
	@Override
	public void useSkill(PlayerInfo p) 
	{
		if (this.currentCooldown == 0)
		{
			GameServer.getInstance().teleport.increaseCount();
			GameServer gameserver = GameServer.getInstance();
			boolean teleported = false;
			int maxRetry = 60;
			int retry = 0;
			while (!teleported && retry < maxRetry)
			{
				int randomCoordX = getRandomNumber (0, gameserver.gameBoard.gridSize);
				int randomCoordY = getRandomNumber (0, gameserver.gameBoard.gridSize);
				if (gameserver.gameBoard.objects[randomCoordX][randomCoordY] instanceof Ground)
				{
					p.coordinate.x = randomCoordX * (gameserver.gameBoard.size / gameserver.gameBoard.gridSize);
					p.coordinate.y = randomCoordY * (gameserver.gameBoard.size / gameserver.gameBoard.gridSize);
					teleported = true;
				}
				retry ++;
			} 
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
	
	private int getRandomNumber(int min, int max) 
	{
	    return (int) ((Math.random() * (max - min)) + min);
	}

}
