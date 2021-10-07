package shared;

import java.util.ArrayList;

public class GameBoard {

    public final int size = 1000;
    public final int gridSize = 20;
    public GameObject[][] objects;
    private AbstractFactory Factory;


    public GameBoard(AbstractFactory factory)
    {
        this.Factory = factory;
        this.objects = new GameObject[gridSize][gridSize];
        this.objects[5][5] = Factory.createWall("AAAAAA",1, 0);
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
