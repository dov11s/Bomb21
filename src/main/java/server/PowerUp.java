package server;

import shared.Player;

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
        if(this.Timer <= 0)
            this.gameobjectdelegate.removeObject(this);
    }

    public void onStep(Player player){
        if(this.isWalkable)
            System.out.println("You gain powerup!");
    }
}
