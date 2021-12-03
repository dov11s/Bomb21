package server;

public class Test {

    private GameBoard board;
    private int x;
    private  int y;

    public Test(GameBoard atnaujinti){
        board = atnaujinti;
        x=3;
        y=3;
    }


    public GameBoard update(){

        board.addWall(x++, y++);

        return board;
    }


}
