package server;

public class StateWaitForPlayers extends GameState 
{
	private int timer = -1;

	@Override
	public void handleUpdate() 
	{
		GameServer gameServer = GameServer.getInstance();
		this.isReadyForNextStage = false;
		if (gameServer.getConnectedPlayerCount() > 2)
		{
			if (this.timer < 0)
			{
				this.timer = 300;
			}
			gameServer.network.sendString("Game is starting in.. " + (this.timer / 60 + 1));
			if (timer == 0)
			{
				gameServer.network.sendString(null);
				this.isReadyForNextStage = true;
				this.timer = -1;
			}
			else
			{
				this.timer --;
			}
		}
		else
		{
			this.timer = -1;
			gameServer.network.sendString("Waiting for players..");
		}
		
	}

}
