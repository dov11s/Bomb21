package server;

public interface BombObservable {
    public void add (BombObserver observer);
    public void remove (BombObserver observer);
    public void notifyObservers ();

}
