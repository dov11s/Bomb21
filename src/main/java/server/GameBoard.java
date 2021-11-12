package server;

import java.util.ArrayList;
import java.util.Random;

import shared.*;

public class GameBoard implements Cloneable{

    public final int size = 1000;
    public final int gridSize = 20;
    public GameObject[][] objects;
    private BombObserver bombObserver;
    private AbstractFactory factory;

    private int currentTick = 0;
    private int powerUpCounter = 0;
    private int timeToCreatePowerUp = 10;

    private IStageBuilder stage1builder;
    private IStageBuilder stage2builder;
    private IStageBuilder stage3builder;

    public GameBoard(AbstractFactory factory)
    {
        this.factory = factory;
        this.objects = new GameObject[gridSize][gridSize];
        this.stage1builder = new Stage1Builder(gridSize);
        this.stage2builder = new Stage2Builder(gridSize);
        this.stage3builder = new Stage3Builder(gridSize);

        StageDirector stageDirector = new StageDirector(stage3builder);

        stageDirector.makeStage();

        Stage stage1 = stageDirector.getStage();

        for(Coordinates kor: stage1.getGrounds())
            this.objects[kor.getX()][kor.getY()] = this.factory.createGround();

        int kiekis = 76;
        boolean sunaikinama = false;

        for(Coordinates kor: stage1.getWalls()){

            this.objects[kor.getX()][kor.getY()] = this.factory.createWall(sunaikinama);
            kiekis-=1;
            if(kiekis == 0) sunaikinama = true;
        }



        bombObserver = new BombObserver(this);


        this.factory.SetBombObserver(bombObserver);





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

    }

    public void paleistiKopija(){
        GameBoard copy = copyDeep();


    }



    public GameBoard copyDeep(){

        try {
            return (GameBoard)this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
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

    private  void spawnPowerUp(){
        Random rand = new Random();

        if(powerUpCounter > 4)
            return;

        while(true){
            int x = rand.nextInt((19 - 0) + 1) + 0;
            int y = rand.nextInt((19 - 0) + 1) + 0;


            if(this.objects[x][y] instanceof Ground){
                this.objects[x][y] = this.factory.createPowerUp();
                powerUpCounter+=1;
                break;
            }

        }








    }


    public void runTick()
    {
        currentTick +=1;

        if(currentTick % (60 * timeToCreatePowerUp)== 0) // kas 10s sukurti nauja powerUp
            spawnPowerUp();

        for(int x = 0; x < this.gridSize; x++)
        {
            for (int y = 0; y < this.gridSize; y++)
            {
            	if(this.objects[x][y].isDead) {



                    if(this.objects[x][y] instanceof PowerUp)
                    {
                        System.out.println("Sunaikinau powerUpa");
                        powerUpCounter-=1;
                    }

                    this.ClearTarget(x, y);



                }

            	objects[x][y].onTick();
            }
        }
    }
    
    public int cellSize()
    {
    	return size / gridSize;
    }
}
