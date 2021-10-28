package server;

public class TeleportTrap extends TrapDecorator
{

	public TeleportTrap(TrapEffect trapEffect) 
	{
		super(trapEffect);
	}

	public void activateTrap(PlayerInfo p) 
	{
		super.activateTrap(p);
		this.trapEffect.activateTrap(p);
	}

	public void onTick() 
	{
		super.onTick();
		this.onTick();		
	}
}
