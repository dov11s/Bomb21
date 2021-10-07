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
        for(int x = 0; x<gridSize; x++){
            for(int y = 0; y<gridSize; y++){
                this.objects[x][y] = this.Factory.createGround("#348C31", 1, 0);
            }
        }
        this.objects[5][5] = Factory.createWall("#DBD3BF",1, 0);
    }
    
    public SimplifiedGameBoard getSimpleGameBoard()
    {
		SimplifiedGameBoard simpleGameboard = new SimplifiedGameBoard();
		
		simpleGameboard.gridSize = this.gridSize;
		simpleGameboard.size = this.size;
        for (int i = 0; i < this.gridSize; i ++) 
        {
            for (int j = 0; j < this.gridSize; j ++)
            {
            	simpleGameboard.objects[i][j].color = this.objects[i][j].color;
//            	simple
            }
        }
        return simpleGameboard;
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
