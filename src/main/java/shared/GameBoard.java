package shared;

import java.util.ArrayList;

public class GameBoard {

    public final int size = 1000;
    public final int gridSize = 20;
    public GameObject[][] objects;


    public GameBoard()
    {
        this.objects = new GameObject[gridSize][gridSize];
        this.objects[5][5] = new Wall("AAAAAA",1);
    }
    public void runTick()
    {
        for (GameObject[] u: objects) 
        {
            for (GameObject object: u)
            {
            	object.onTick();
            }
        }
    }
}
