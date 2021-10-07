package shared;

public class PowerUp extends GameObject{

    private int Timer;

    public PowerUp(String color, float alpha, int timer){
        super(color, alpha);
        this.Timer = timer;
    }

    public void onDamage(){
        if(this.isDestroyable)
            System.out.println("Wall has been destroyed");
    }
    public void onTick(){
        this.Timer--;
    }

    public void onStep(){
        if(this.isWalkable)
            System.out.println("You gain powerup!");
    }
}
