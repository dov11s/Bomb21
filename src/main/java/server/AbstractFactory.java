package server;

public abstract class AbstractFactory {
    public abstract GameObject createTrap();
    public abstract GameObject createWall();
    public abstract GameObject createBomb();
    public abstract GameObject createPowerUp();
    public abstract GameObject createGround();
}
