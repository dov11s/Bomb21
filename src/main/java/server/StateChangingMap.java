package server;

public class StateChangingMap extends GameState
{
	private int timer = -1;

	@Override
	public void handleUpdate() 
	{
		GameServer gameServer = GameServer.getInstance();
		this.isReadyForNextStage = false;
		if (this.timer == -1)
		{
			this.timer = 300;
		}
		gameServer.network.sendString("Game won by: "+ gameServer.getWinner() + " new game in... " + this.timer / 60);
		if (timer == 0)
		{
			this.isReadyForNextStage = true;
			this.timer = -1;
			gameServer.setGameLevel();
		}
		else
		{
			this.timer --;
		}
	
	}
	
}
