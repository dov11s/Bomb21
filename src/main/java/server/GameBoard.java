package server;

import java.util.ArrayList;
import java.util.List;
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
    private int trapCounter = 0;
    private int timeToCreatePowerUp = 10;
    private int timeToCreateTrap = 10;

    private int level;

    private IStageBuilder stage1builder;
    private IStageBuilder stage2builder;
    private IStageBuilder stage3builder;

    public GameBoard(AbstractFactory factory, IStageBuilder builder, int level)
    {
        this.factory = factory;
        this.objects = new GameObject[gridSize][gridSize];
        this.level = level;

        StageDirector stageDirector = new StageDirector(builder);

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




        if(level == 3){
            startingTraps();
        }
    }

    private void startingTraps(){
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








    public void addObject(int x, int y, String type){

        switch (type){
            case "wall":
                this.objects[x][y] = this.factory.createWall(false);
                break;
            case "desWall":
                this.objects[x][y] = this.factory.createWall(true);
                break;
            case "trap":
                this.objects[x][y] = this.factory.createTrap();
                Trap modified3 = (Trap) this.objects[x][y];
                modified3.setTrapeffect( new DamageTrap  (new SlowTrap (new ConcreteTrap())));
                this.objects[x][y] = modified3;
                break;
        }


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

        GameServer server = GameServer.getInstance();

        server.bomb.increaseCount();
    }

    public void spawnWall()
    {
        System.out.println("Turim ikelti siena ");
        this.objects[2][2] = this.factory.createWall(false);

    }

    public void addWall(int x, int y)
    {
        System.out.println("Turim ikelti siena ");
        this.objects[x][y] = this.factory.createWall(false);

    }






    public void ClearTarget(int x, int y, boolean fire){

//        if(fire){
//            this.objects[x][y] = this.factory.createGround();
//            this.objects[x][y].explodeAnimation = 30;
//        }else
//            this.objects[x][y] = this.factory.createGround();

        this.objects[x][y] = this.factory.createGround();
        System.out.println("Removing Item from location " + x + " " + y);
    }

    public void ClearTarget(List<Vector2f> bLoc){

        for (Vector2f xy : bLoc){

            if((int)xy.x < gridSize-1 && (int)xy.y < gridSize-1){
                this.objects[(int)xy.x][(int)xy.y] = this.factory.createGround();
                this.objects[(int)xy.x][(int)xy.y].explodeAnimation = 30;
            }



//            if(this.objects[(int)xy.x][(int)xy.y]  instanceof Ground && this.objects[(int)xy.x][(int)xy.y]  instanceof Wall){
//                this.objects[(int)xy.x][(int)xy.y] = this.factory.createGround();
//                this.objects[(int)xy.x][(int)xy.y].explodeAnimation = 30;
//            }



        }

    }
    
    public SimplifiedGameBoard getSimpleGameBoard()
    {
		SimplifiedGameBoard simpleGameboard = new SimplifiedGameBoard(this.size, this.gridSize);
		
        for (int i = 0; i < this.gridSize; i ++) 
        {
            for (int j = 0; j < this.gridSize; j ++)
            {
            	if (this.objects[i][j] instanceof Ground)
            	{
                	simpleGameboard.objects[i][j] =  new SimplifiedGameObject(this.objects[i][j].color, ObjectType.GROUND, this.objects[i][j].explodeAnimation > 0);	
            	}
            	else if (this.objects[i][j] instanceof Wall)
            	{
                	simpleGameboard.objects[i][j] =  new SimplifiedGameObject(this.objects[i][j].color, ObjectType.WALL, this.objects[i][j].explodeAnimation > 0);	
            	}
            	else if (this.objects[i][j] instanceof Trap)
            	{
                	simpleGameboard.objects[i][j] =  new SimplifiedGameObject(this.objects[i][j].color, ObjectType.TRAP, this.objects[i][j].explodeAnimation > 0);	
            	}
            	else if (this.objects[i][j] instanceof PowerUp)
            	{
                	simpleGameboard.objects[i][j] =  new SimplifiedGameObject(this.objects[i][j].color, ObjectType.POWERUP, this.objects[i][j].explodeAnimation > 0);	
            	}
            	else if (this.objects[i][j] instanceof Bomb)
            	{
                	simpleGameboard.objects[i][j] =  new SimplifiedGameObject(this.objects[i][j].color, ObjectType.BOMB, this.objects[i][j].explodeAnimation > 0);	
            	}
            	else
            	{
                	simpleGameboard.objects[i][j] =  new SimplifiedGameObject(this.objects[i][j].color, ObjectType.GROUND, this.objects[i][j].explodeAnimation > 0);	
            	}
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

    private  void spawnTrap(){
        Random rand = new Random();

        if(trapCounter > 8)
            return;

        while(true){
            int x = rand.nextInt((19 - 0) + 1) + 0;
            int y = rand.nextInt((19 - 0) + 1) + 0;

            if(this.objects[x][y] instanceof Ground){

                this.objects[x][y] = createTrap(x, y);
                trapCounter+=1;
                break;
            }

        }

    }


    private Trap createTrap(int x, int y){

        this.objects[x][y] = this.factory.createTrap();
        Trap modified3 = (Trap) this.objects[x][y];

        Random rand = new Random();

        int type = rand.nextInt(8);

        System.out.println("Gautas skaicius: " + type);

        switch (type){
            case 0:
            default:
                modified3.setTrapeffect(new DamageTrap (new ConcreteTrap()));
                System.out.println("0 trapas");
                break;
            case 1:
                modified3.setTrapeffect(new TeleportTrap (new ConcreteTrap()));
                System.out.println("1 trapas");
                break;
            case 2:
                modified3.setTrapeffect(new SlowTrap (new ConcreteTrap()));
                System.out.println("2 trapas");
                break;
            case 3:
                modified3.setTrapeffect( new DamageTrap (new TeleportTrap (new SlowTrap (new ConcreteTrap()))));
                System.out.println("3 trapas");
                break;
            case 4:
                modified3.setTrapeffect(new ConcreteTrap());
                System.out.println("4 trapas");
                break;
            case 5:
                modified3.setTrapeffect( new DamageTrap (new TeleportTrap  (new ConcreteTrap())));
                System.out.println("5 trapas");
                break;
            case 6:
                modified3.setTrapeffect( new DamageTrap (new SlowTrap  (new ConcreteTrap())));
                System.out.println("6 trapas");
                break;
            case 7:
                modified3.setTrapeffect( new TeleportTrap (new SlowTrap  (new ConcreteTrap())));
                System.out.println("7 trapas");
                break;

        }


        return modified3;
    }



    public void runTick()
    {
        currentTick +=1;

        if(currentTick % (60 * timeToCreatePowerUp)== 0 && level > 1) // kas 10s sukurti nauja powerUp
            spawnPowerUp();

        if(currentTick % (60 * timeToCreateTrap)== 0 && level > 2) // kas 10s sukurti nauja trapa
            spawnTrap();


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

                    this.ClearTarget(x, y, false);



                }
            	if (objects[x][y].explodeAnimation > 0) objects[x][y].explodeAnimation --;
            	objects[x][y].onTick();
            }
        }
    }
    
    public int cellSize()
    {
    	return size / gridSize;
    }
}
