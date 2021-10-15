package server;

import shared.Player;

public class Bomb extends GameObject {

    private int Timer;
    private int OwnerId;
    private BombObserver observer;

    public Bomb(String color, float alpha, int timer, int ownerid, BombObserver observer){
        super(color, alpha);
        this.Timer = timer;
        this.OwnerId = ownerid;
        this.observer = observer;
        this.isWalkable = true;
    }

    public void onDamage(){
        //cannot be destroyed?
    }
    public void onTick(){
        this.Timer--;
        if(this.Timer <= 0)
            observer.explode(this);
    }

    public void onStep(Player player){
        //cannot be stepped on?
    }
}
