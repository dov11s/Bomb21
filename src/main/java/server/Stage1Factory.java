package server;


import java.util.Hashtable;
import java.util.Random;

public class Stage1Factory extends AbstractFactory{

    private Hashtable<String, String> colors;

    public Stage1Factory(){
        this.colors = new Hashtable<>();
        this.colors.put("Wall", "#2e2203");
        this.colors.put("DesWall", "#8c8674");
        this.colors.put("Ground", "#348C31");
        this.colors.put("Bomb", "#6A2E35");
        this.colors.put("PowerUp", "#BAD094");
        this.colors.put("Trap", "#373D20");
    }

    @Override
    public GameObject createTrap() 
    {
        return new Trap(this.colors.get("Trap"), 1, new ConcreteTrap());
    }

    @Override
    public GameObject createWall(boolean destroyable)
    {
        String wallType = "Wall";

        if(destroyable)
            wallType = "DesWall";

        return new Wall(this.colors.get(wallType), 1, destroyable);
    }

    @Override
    public GameObject createBomb(int ownerid) {
        return new Bomb(colors.get("Bomb"), 1, 90, ownerid, this.bombObserver);
    }

    @Override
    public GameObject createPowerUp() {

        PowUpFactory powFactory = new PowUpFactory();
        PowUp powUp = null;

        Random rand = new Random();
        int randomNum = rand.nextInt((5 - 1) + 1) + 1;

        powUp = powFactory.makePowUp(randomNum);


        return new PowerUp(powUp, 1, 5);
    }

    @Override
    public GameObject createGround()
    {
    	return new Ground(colors.get("Ground"), 1);
    }

}
