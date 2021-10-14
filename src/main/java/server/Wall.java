package server;

import shared.Player;

public class Wall extends GameObject{

    public Wall(String color, float alpha){
        super(color, alpha);

    }

    public void onDamage()
    {
        if(this.isDestroyable)
            this.gameobjectdelegate.removeObject(this);
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
