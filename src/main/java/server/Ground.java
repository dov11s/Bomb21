package server;

public class Ground extends GameObject{

    public Ground(String color, float alpha){
        super(color, alpha);
        this.isWalkable = true;
    }

    @Override
    public void onDamage() {

    }

    @Override
    public void onTick() {

    }

    @Override
    public void onStep(Player player) {

    }
}
