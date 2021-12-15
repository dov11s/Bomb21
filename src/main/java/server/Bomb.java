package server;

import java.util.List;
import java.util.Stack;

public class Bomb extends GameObject implements BombObservable{

    private int Timer;
    private int OwnerId;
    private int ExplosionRadius = 2;
    private List<BombObserver> observers = new Stack<BombObserver>();

    public Bomb(String color, float alpha, int timer, int ownerid, BombObserver observer){
        super(color, alpha);
        this.Timer = timer;
        this.OwnerId = ownerid;
        this.add(observer);
        this.isWalkable = true;
    }

    public void onDamage(){
        //cannot be destroyed?
    }
    public void onTick(){
        this.Timer--;
        if(this.Timer <= 0)
            notifyObservers();
    }

    public void onStep(PlayerInfo player){
        //cannot be stepped on?
    }

    public void add(BombObserver observer)
    {
        this.observers.add(observer);
    }

    public void remove(BombObserver observer)
    {
        this.observers.remove(observer);
    }

    public void notifyObservers()
    {
        for (BombObserver observer:
             observers) {
            observer.explode(this);

        }
    }

    public int explosionRadius() {
        if (this.Timer <= 0) return this.ExplosionRadius;
        else return 0;
    }
}
