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

        walls.add(new Coordinates(5, 4));
        walls.add(new Coordinates(5, 6));
        walls.add(new Coordinates(6, 5));

        walls.add(new Coordinates(15, 16));
        walls.add(new Coordinates(15, 14));
        walls.add(new Coordinates(14, 15));

        for(int x = 1; x < 19; x++){
            walls.add(new Coordinates(x, 11));
        }

        System.out.println("Koks dydis Po: " + walls.size());

        stage.setWalls(walls);
    }

    public Stage getStage(){
        return this.stage;
    }


}
