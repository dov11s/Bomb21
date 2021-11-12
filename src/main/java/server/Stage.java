package server;

import java.util.List;

public class Stage implements  IStagePlan{


    private List<Coordinates> grounCor;
    private List<Coordinates> wallCor;

    @Override
    public void setGrounds(List<Coordinates> groundCor) {
        this.grounCor = groundCor;
    }

    @Override
    public void setWalls(List<Coordinates> wallCor) {
        this.wallCor = wallCor;
    }

    public List<Coordinates> getGrounds(){
        return  this.grounCor;
    }

    public List<Coordinates> getWalls(){
        return  this.wallCor;
    }








}
