package server;

import java.util.Hashtable;

public class Stage1Factory extends AbstractFactory{

    private Hashtable<String, String> colors;

    public Stage1Factory(){
        this.colors = new Hashtable<>();
        this.colors.put("Wall", "#DBD3BF");
        this.colors.put("Ground", "#348C31");
        this.colors.put("Bomb", "#6A2E35");
        this.colors.put("PowerUp", "#B6D094");
        this.colors.put("Trap", "#373D20");
    }



    @Override
    public GameObject createTrap() {
        return new Trap(this.colors.get("Trap"), 1);
    }

    @Override
    public GameObject createWall()
    {
        return new Wall(this.colors.get("Wall"), 1);
    }

    @Override
    public GameObject createBomb() {
        return new Bomb(colors.get("Bomb"), 1, 5);
    }

    @Override
    public GameObject createPowerUp() {
        return new PowerUp(colors.get("PowerUp"), 1, 5);
    }

    @Override
    public GameObject createGround()
    {
    	return new Ground(colors.get("Ground"), 1);
    }

}
