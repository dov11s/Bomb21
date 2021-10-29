package server;

public class ConcreteTrap implements TrapEffect
{
	private PlayerInfo p;
	boolean isDone = false;
	private final int timer = 1 * 60; //second
	private int currentTimer = -1;
	@Override
	public void activateTrap(PlayerInfo p) 
	{
		this.p = p;
		this.p.speed = 0;
		GameServer gameserver = GameServer.getInstance();
		gameserver.players.get(this.p.id).speed = this.p.speed;
		this.currentTimer = timer;
	}

	@Override
	public void onTick() 
	{

		if (this.currentTimer > 1)
		{
			this.currentTimer --;
		}
		else if (this.currentTimer == 1)
		{
			GameServer gameserver = GameServer.getInstance();
			gameserver.players.get(this.p.id).speed = this.p.baseSpeed;
			this.currentTimer --;
		}
		else if (this.currentTimer == 0)
		{
			this.isDone = true;
		}
		
	}

	@Override
	public boolean isDone()
	{
		return isDone;
	}

}
