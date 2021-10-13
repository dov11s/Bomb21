package server;

import java.util.ArrayList;

import shared.ObjectType;
import shared.SimplifiedGameBoard;
import shared.SimplifiedGameObject;

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
                if(x == 0 || x == gridSize-1 || y == 0 || y == gridSize -1)
                    this.objects[x][y] = this.Factory.createWall("#DBD3BF", 1, 0);
                else
                    this.objects[x][y] = this.Factory.createGround("#348C31", 1, 0);
            }
        }
        this.objects[5][4] = this.Factory.createWall("#DBD3BF",1, 0);
        this.objects[5][6] = this.Factory.createWall("#DBD3BF",1, 0);
        this.objects[6][5] = this.Factory.createWall("#DBD3BF",1, 0);

        this.objects[15][16] = this.Factory.createWall("#DBD3BF",1, 0);
        this.objects[15][14] = this.Factory.createWall("#DBD3BF",1, 0);
        this.objects[14][15] = this.Factory.createWall("#DBD3BF",1, 0);
    }
    
    public SimplifiedGameBoard getSimpleGameBoard()
    {
		SimplifiedGameBoard simpleGameboard = new SimplifiedGameBoard(this.size, this.gridSize);
		
        for (int i = 0; i < this.gridSize; i ++) 
        {
            for (int j = 0; j < this.gridSize; j ++)
            {
            	simpleGameboard.objects[i][j] =  new SimplifiedGameObject(this.objects[i][j].color, ObjectType.GROUND);
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
    
    public int cellSize()
    {
    	return size / gridSize;
    }
}
