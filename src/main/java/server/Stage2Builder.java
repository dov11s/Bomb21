package server;

import java.util.ArrayList;
import java.util.List;

public class Stage2Builder implements IStageBuilder{
    private Stage stage;
    private int gridSize;

    public Stage2Builder(int gridSize){
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




        for(int i = 7; i<11; i++){
            walls.add(new Coordinates(i, 9));
            walls.add(new Coordinates(i, 13));
            walls.add(new Coordinates(i, 17));
        }

        for(int i =10; i<17; i++){
            if(i != 13){
                walls.add(new Coordinates(6, i));
                walls.add(new Coordinates(11, i));
            }
        }






        System.out.println("Koks dydis Po: " + walls.size());

        stage.setWalls(walls);
    }

    public Stage getStage(){
        return this.stage;
    }

}
