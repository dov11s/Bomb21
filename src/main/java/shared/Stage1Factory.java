package shared;

public class Stage1Factory extends AbstractFactory{

    @Override
    public GameObject createTrap(String color, float alpha, int timer) {
        return new Trap(color, alpha);
    }

    @Override
    public GameObject createWall(String color, float alpha, int timer) {
        return new Wall(color, alpha);
    }

    @Override
    public GameObject createBomb(String color, float alpha, int timer) {
        return new Bomb(color, alpha, timer);
    }

    @Override
    public GameObject createPowerUp(String color, float alpha, int timer) {
        return new PowerUp(color, alpha, timer);
    }
}
