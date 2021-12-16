package server;

public class SlowTrap extends TrapDecorator
{

	boolean isDone = false;
	boolean started = false;
	private PlayerInfo p;
	private final int timer = 2 * 60; //2 seconds
	private int currentTimer = -1;
	public SlowTrap(TrapEffect trapEffect) 
	{
		super(trapEffect);
	}

	public void activateTrap(PlayerInfo p) 
	{
		super.activateTrap(p);
		this.p = p;
	}

	public void onTick() 
	{
		super.onTick();
		slowPlayer(p);
	}

	public void slowPlayer(PlayerInfo p)
	{
		if (super.isDone() && !started)
		{
			this.p.speed = 0;
			GameServer gameserver = GameServer.getInstance();
			gameserver.players.get(this.p.id).speed =  this.p.baseSpeed * 0.5f;
			this.currentTimer = timer;
			started = true;
		}
		
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
	public boolean isDone() 
	{
		return super.isDone() && isDone;
	}
}
