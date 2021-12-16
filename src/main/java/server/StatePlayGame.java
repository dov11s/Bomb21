package server;

public class StatePlayGame extends GameState
{
	private final int gameTime = 60; // 1 minute
	private int timer = 0;
	private boolean playersDead = false;
	
	@Override
	public void handleUpdate() 
	{
		GameServer gameServer = GameServer.getInstance();
		this.isReadyForNextStage = false;
		if (timer > gameTime * 60 || playersDead)
		{
			this.isReadyForNextStage = true;
			this.timer = 0;
			this.playersDead = false;
		}
		else
		{
			if (gameServer.checkIfPlayerDead())
			{
				this.playersDead = true;
				gameServer.enableMovement = false;
			}
			gameServer.enableMovement = true;
			this.timer ++;
		}
		
	}

}
