package server;

public abstract class AbstractFactory {
    protected BombObserver bombObserver;
    public abstract GameObject createTrap();
    public abstract GameObject createWall(boolean destroyable);
    public abstract GameObject createBomb(int ownerid);
    public abstract GameObject createPowerUp();
    public abstract GameObject createGround();

    public void SetBombObserver(BombObserver observer){
        this.bombObserver = observer;
    }
}
