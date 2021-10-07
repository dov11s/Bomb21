package shared;

import java.util.ArrayList;

public class GameBoard {

    public int sizeX;
    public int sizeY;
    public ArrayList<ArrayList<GameObject>> objects;


    public GameBoard()
    {
        objects = new ArrayList<>(20);
    }
}
