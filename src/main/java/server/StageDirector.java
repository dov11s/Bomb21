package server;

public class StageDirector {
    private IStageBuilder stageBuilder;

    public StageDirector(IStageBuilder stageBuilder){
        this.stageBuilder = stageBuilder;
    }

    public Stage getStage(){
        return  this.stageBuilder.getStage();
    }


    public void makeStage(){
        this.stageBuilder.buildWall();
        this.stageBuilder.buildGround();
    }
}
