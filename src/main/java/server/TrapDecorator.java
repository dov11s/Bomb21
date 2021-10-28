package server;

public abstract class TrapDecorator implements TrapEffect
{
	protected TrapEffect trapEffect;
	
	public TrapDecorator(TrapEffect trapEffect)
	{
		this.trapEffect = trapEffect;
	}

	@Override
	public void activateTrap(PlayerInfo p) 
	{
		this.trapEffect.activateTrap(p);
	}

	@Override
	public void onTick() 
	{
		this.onTick();		
	}

}
