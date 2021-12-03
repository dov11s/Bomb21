package server;

public interface Chain {

    public void setNextChain(Chain nextChain);
    public GameBoard result(Task text);
}

