package server;

import java.util.List;

public interface IStagePlan {

    public void setGrounds(List<Coordinates> groundCor);

    public void setWalls(List<Coordinates> wallCor);

}
