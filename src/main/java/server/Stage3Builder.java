package server;

import java.util.ArrayList;
import java.util.List;

public class Stage3Builder implements IStageBuilder{
    private Stage stage;
    private int gridSize;

    public Stage3Builder(int gridSize){
        this.stage = new Stage();
        this.gridSize = gridSize;
    }

    @Override
    public void buildGround() {
        List<Coordinates> ground = new ArrayList<Coordinates>();

        for(int x = 0; x<gridSize; x++){
            for(int y = 0; y<gridSize; y++){
                if(x != 0 && x != gridSize-1 && y != 0 && y != gridSize -1)
                    ground.add(new Coordinates(x, y));
            }
        }


        stage.setGrounds(ground);
    }

    @Override
    public void buildWall() {
        List<Coordinates> walls = new ArrayList<Coordinates>();

        for(int x = 0; x<gridSize; x++){
            for(int y = 0; y<gridSize; y++){
                if(x == 0 || x == gridSize-1 || y == 0 || y == gridSize -1)
                    walls.add(new Coordinates(x, y));
            }
        }

        System.out.println("Koks dydis Pries: " + walls.size());


        walls.add(new Coordinates(3, 14-1));
        walls.add(new Coordinates(4, 14-1));
        walls.add(new Coordinates(5, 14-1));
        walls.add(new Coordinates(6, 14-1));
        walls.add(new Coordinates(3, 18-1));
        walls.add(new Coordinates(4, 18-1));
        walls.add(new Coordinates(5, 18-1));
        walls.add(new Coordinates(6, 18-1));
        walls.add(new Coordinates(3, 17-1));
        walls.add(new Coordinates(3, 16-1));
        walls.add(new Coordinates(3, 15-1));
        walls.add(new Coordinates(5, 16-1));
        walls.add(new Coordinates(6, 16-1));
        walls.add(new Coordinates(6, 15-1));





        walls.add(new Coordinates(8, 14-1));
        walls.add(new Coordinates(8, 15-1));
        walls.add(new Coordinates(8, 16-1));
        walls.add(new Coordinates(8, 17-1));
        walls.add(new Coordinates(11, 14-1));
        walls.add(new Coordinates(11, 15-1));
        walls.add(new Coordinates(11, 16-1));
        walls.add(new Coordinates(11, 17-1));
        walls.add(new Coordinates(9, 16-1));
        walls.add(new Coordinates(10, 16-1));
        walls.add(new Coordinates(9, 18-1));
        walls.add(new Coordinates(10, 18-1));



        walls.add(new Coordinates(13, 14-1));
        walls.add(new Coordinates(14, 14-1));
        walls.add(new Coordinates(15, 14-1));
        walls.add(new Coordinates(16, 14-1));

        walls.add(new Coordinates(13, 15-1));
        walls.add(new Coordinates(13, 16-1));
        walls.add(new Coordinates(13, 17-1));
        walls.add(new Coordinates(13, 18-1));


        for(int i = 8; i<12; i++){
            walls.add(new Coordinates(i, 11-1));
            walls.add(new Coordinates(i, 8-1));
            walls.add(new Coordinates(i, 5-1));
        }

        walls.add(new Coordinates(8, 10-1));
        walls.add(new Coordinates(8, 9-1));
        walls.add(new Coordinates(11, 10-1));
        walls.add(new Coordinates(11, 9-1));
        walls.add(new Coordinates(11, 7-1));
        walls.add(new Coordinates(11, 6-1));


        System.out.println("Koks dydis Po: " + walls.size());

        stage.setWalls(walls);
    }

    public Stage getStage(){
        return this.stage;
    }


}
