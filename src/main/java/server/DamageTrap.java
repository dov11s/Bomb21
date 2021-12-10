package server;

public class DamageTrap extends TrapDecorator
{

	boolean isDone = false;
	public DamageTrap(TrapEffect trapEffect) 
	{
		super(trapEffect);
	}

	public void activateTrap(PlayerInfo p) 
	{
		super.activateTrap(p);
		damagePlayer(p);
	}

	private void damagePlayer(PlayerInfo p)
	{
		GameServer gameserver = GameServer.getInstance();
		gameserver.players.get(p.id).health --;



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
}
