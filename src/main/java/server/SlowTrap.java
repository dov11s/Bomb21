package server;

public class SlowTrap extends TrapDecorator
{

	public SlowTrap(TrapEffect trapEffect) 
	{
		super(trapEffect);
	}

	public void activateTrap(PlayerInfo p) 
	{
		super.activateTrap(p);
	}

	public void onTick() 
	{
		super.onTick();	
	}
}
