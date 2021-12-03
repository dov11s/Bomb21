package server;

public class Task {
    private int quantity;
    private GameBoard board;
    private String what;
    private  String where;

    public Task(int quantity, String what, String where, GameBoard newboard){
        this.quantity = quantity;
        this.what = what;
        this.where = where;
        board = newboard;
    }



    public int getQuantity(){
        return  quantity;
    }

    public String getWhat(){
        return  what;
    }

    public String getWhere(){
        return  where;
    }

    public GameBoard getBoard(){
        return board;
    }
}
