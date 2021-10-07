package server;

import shared.Player;

public class Trap extends GameObject{


    public Trap(String color, float alpha){
        super(color, alpha);
    }

    public void onDamage(){
        if(this.isDestroyable)
            System.out.println("Trap has been destroyed!");
    }
    public void onTick(){

    }

    public void onStep(Player player){
        //initiate trap
    }
}
