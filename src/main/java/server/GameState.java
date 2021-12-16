package server;

public abstract class GameState
{
		private GameState nextState;
		protected boolean isReadyForNextStage = false;
		
		public void setNextState(GameState nextState) {
			this.nextState = nextState;		
		}
		
		public void getNextState( GameServer context )
		{
			context.setState(nextState);
		}
		
		public abstract void handleUpdate();
		
		public boolean isReadyForNextStage()
		{
			return isReadyForNextStage;
		}
		
}
	
