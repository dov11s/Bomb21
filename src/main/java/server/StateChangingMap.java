package server;

public class StateChangingMap extends GameState
{
	private int timer = -1;

	@Override
	public void handleUpdate() 
	{
		GameServer gameServer = GameServer.getInstance();
		gameServer.enableMovement = false;
		this.isReadyForNextStage = false;
		if (this.timer == -1)
		{
			this.timer = 300;
		}
		gameServer.network.sendString("Winner player: "+ gameServer.getWinner() + " map refresh in... " + (this.timer / 60 + 1));
		if (timer == 0)
		{
			gameServer.network.sendString(null);
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
