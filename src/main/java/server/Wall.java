package server;

import shared.Player;

public class Wall extends GameObject{


    public Wall(String color, float alpha, boolean destroyable){
        super(color, alpha);
        this.isDestroyable = destroyable;
    }

    public void onDamage()
    {
        if(this.isDestroyable)
            isDead = true;
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
