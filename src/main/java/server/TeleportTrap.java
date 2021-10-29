package server;

public class TeleportTrap extends TrapDecorator
{

	boolean isDone = false;
	public TeleportTrap(TrapEffect trapEffect) 
	{
		super(trapEffect);
	}

	public void activateTrap(PlayerInfo p) 
	{
		super.activateTrap(p);
		teleportPlayer(p);

	}
	
	private void teleportPlayer(PlayerInfo p)
	{
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
		isDone = true;
	}

	public void onTick() 
	{
		super.onTick();	
	}

	@Override
	public boolean isDone() 
	{
		return super.isDone() && isDone;
	}
	
	private int getRandomNumber(int min, int max) 
	{
	    return (int) ((Math.random() * (max - min)) + min);
	}
}
