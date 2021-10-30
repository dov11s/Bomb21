package server;

import java.util.ArrayList;

import shared.*;

public class GameBoard {

    public final int size = 1000;
    public final int gridSize = 20;
    public GameObject[][] objects;
    private BombObserver bombObserver;
    private AbstractFactory factory;


    public GameBoard(AbstractFactory factory)
    {
        this.factory = factory;
        this.objects = new GameObject[gridSize][gridSize];
        for(int x = 0; x<gridSize; x++){
            for(int y = 0; y<gridSize; y++){
                if(x == 0 || x == gridSize-1 || y == 0 || y == gridSize -1)
                    this.objects[x][y] = this.factory.createWall(false);
                else
                    this.objects[x][y] = this.factory.createGround();
            }
        }

        bombObserver = new BombObserver(this);
        this.factory.SetBombObserver(bombObserver);
        this.objects[5][4] = this.factory.createWall(true);
        this.objects[5][6] = this.factory.createWall(true);
        this.objects[6][5] = this.factory.createWall(true);
        
        //Powerup
        this.objects[1][1] = this.factory.createPowerUp();
        this.objects[3][1] = this.factory.createPowerUp();
        this.objects[5][1] = this.factory.createPowerUp();
        this.objects[7][1] = this.factory.createPowerUp();
        
        //test
        
        //TODO wrong but ok for now
        this.objects[17][3] = this.factory.createTrap();
       
        this.objects[15][3] = this.factory.createTrap();
        Trap modified = (Trap) this.objects[15][3]; 
        modified.setTrapeffect(new DamageTrap (new ConcreteTrap()));
        this.objects[15][3] = modified;
        
        this.objects[13][3] = this.factory.createTrap();
        Trap modified1 = (Trap) this.objects[13][3]; 
        modified1.setTrapeffect(new TeleportTrap (new ConcreteTrap()));
        this.objects[13][3] = modified1;

        this.objects[11][3] = this.factory.createTrap();
        Trap modified2 = (Trap) this.objects[11][3]; 
        modified2.setTrapeffect(new SlowTrap (new ConcreteTrap()));
        this.objects[11][3] = modified2;

        this.objects[9][3] = this.factory.createTrap();
        Trap modified3 = (Trap) this.objects[9][3]; 
        modified3.setTrapeffect( new DamageTrap (new TeleportTrap (new SlowTrap (new ConcreteTrap()))));
        this.objects[9][3] = modified3;

        
        for(int x = 1; x < 19; x++)
            this.objects[x][11] = this.factory.createWall(true);

        this.objects[15][16] = this.factory.createWall(true);
        this.objects[15][14] = this.factory.createWall(true);
        this.objects[14][15] = this.factory.createWall(true);
    }

    public void SpawnBomb(PlayerInfo player)
    {
        int x = Math.round(player.coordinate.x/(size/gridSize));
        int y = Math.round(player.coordinate.y/(size/gridSize));
        this.objects[x][y] = this.factory.createBomb(player.id);

    }

    public void ClearTarget(int x, int y){
        this.objects[x][y] = this.factory.createGround();
        System.out.println("Removing bomb from location " + x + " " + y);
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
        for(int x = 0; x < this.gridSize; x++)
        {
            for (int y = 0; y < this.gridSize; y++)
            {
            	if(this.objects[x][y].isDead) this.ClearTarget(x, y);
            	
            	objects[x][y].onTick();
            }
        }
    }
    
    public int cellSize()
    {
    	return size / gridSize;
    }
}
