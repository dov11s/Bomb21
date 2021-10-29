package server;

public class DamageTrap extends TrapDecorator
{

	public DamageTrap(TrapEffect trapEffect) 
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

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}
}
