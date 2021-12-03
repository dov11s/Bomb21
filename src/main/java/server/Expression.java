package server;

public abstract class Expression {

    public abstract GameBoard randomly(int quantity, GameBoard board);
    public abstract GameBoard linear(int quantity, GameBoard board);
    public abstract GameBoard zigzag(int quantity, GameBoard board);



}
