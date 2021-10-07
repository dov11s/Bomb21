package shared;

import java.util.ArrayList;

public class GameBoard {

    public final int sizeX = 20;
    public final int sizeY = 20;
    GameObject[][] objects;


    public GameBoard()
    {
        this.objects = new GameObject[sizeX][sizeY];
        this.objects[5][5] = new Wall("AAAAAA",1);
    }
}
