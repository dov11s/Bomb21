package server;

public abstract class AbstractFactory {
    public abstract GameObject createTrap(String color, float alpha, int timer);
    public abstract GameObject createWall(String color, float alpha, int timer);
    public abstract GameObject createBomb(String color, float alpha, int timer);
    public abstract GameObject createPowerUp(String color, float alpha, int timer);
    public abstract GameObject createGround(String color, float alpha, int timer);
}
