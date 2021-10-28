package server;

public class Trap extends GameObject{


	private TrapEffect trapeffect;
	
    public Trap(String color, float alpha)
    {
        super(color, alpha);
        this.trapeffect = new ConcreteTrap();
    }

    public void onDamage(){
        if(this.isDestroyable)
            System.out.println("Trap has been destroyed!");
    }
    public void onTick(){

    }

    public void onStep(PlayerInfo player){
        //initiate trap
    }
}
