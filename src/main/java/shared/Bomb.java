package shared;

public class Bomb extends GameObject {

    private int Timer;

    public Bomb(String color, float alpha, int timer){
        super(color, alpha);
        this.Timer = timer;
    }

    public void onDamage(){
        //cannot be destroyed?
    }
    public void onTick(){
        this.Timer--;
        if(this.Timer <= 0)
            this.gameobjectdelegate.removeObject(this);
    }

    public void onStep(Player player){
        //cannot be stepped on?
    }
}
