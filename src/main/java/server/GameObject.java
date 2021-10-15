package server;

import shared.Player;

public abstract class GameObject {
    public boolean isWalkable;
    public boolean isDestroyable;
    public boolean isDead;

    GameObjectDelegate gameobjectdelegate;

    public String color;
    public float alpha;

    public GameObject(String color, float alpha) {
        super();
        this.alpha = alpha;
        this.color = color;
    }

    public void sayHello() {
      System.out.println("GameObject");
    }

    public void setDestroyable(boolean option){
        this.isDestroyable = option;
    }

    public void setWalkable(boolean option){
        this.isWalkable = option;
    }

    public abstract void onDamage();
    public abstract void onTick();
    public abstract void onStep(Player player);

}