package shared;

public class Wall extends GameObject{

    public Wall(String color, float alpha){
        super(color, alpha);

    }

    public void onDamage()
    {
        if(this.isDestroyable)
            System.out.println("Wall has been destroyed");
    }
    
    public void onTick()
    {

    }
    
    public void onStep(Player player)
    {
        if(!this.isWalkable)
            System.out.println("Can't walk on this!");
    }
}
