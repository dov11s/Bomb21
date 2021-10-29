package server;

public class Trap extends GameObject{


	private TrapEffect trapeffect;
	private boolean isStepped = false;
	
	public Trap(String color, float alpha, TrapEffect trapeffect)
    {
        super(color, alpha);
        this.trapeffect = trapeffect;
    }

    public void onDamage()
    {
        if(this.isDestroyable)
        	isDead = true;
    }
    public void onTick()
    {
    	this.trapeffect.onTick();
    }

    public void onStep(PlayerInfo player)
    {
    	if (!isStepped) 
    	{
    		this.trapeffect.activateTrap(player);
    	}
    }
    
    public TrapEffect getTrapeffect() 
    {
		return trapeffect;
	}

	public void setTrapeffect(TrapEffect trapeffect) 
	{
		this.trapeffect = trapeffect;
	}

}
