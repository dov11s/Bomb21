package server;
import java.util.Random;

public class PowerUp extends GameObject
{
    private int Timer;
	private PowUp powUp;

    public PowerUp(PowUp powUp, float alpha, int timer){


        super(powUp.getColor(), alpha);
		this.powUp = powUp;
        this.Timer = timer;
        this.isWalkable = true;



    }

    public void onDamage(){
        if(this.isDestroyable)
            System.out.println("Wall has been destroyed");
    }
    public void onTick(){
//        this.Timer--;
//        if(this.Timer <= 0)
//        	isDead = true;
    }








    public void onStep(PlayerInfo player){
        if(this.isWalkable)
        {
        	if (!isDead)
        	{
				GameServer gameServer = GameServer.getInstance();


				if(powUp.getName().equals("Jump"))
					gameServer.players.get(player.id).setSkillAlgorithm(new JumpSkill());

				else if(powUp.getName().equals("Dash"))
					gameServer.players.get(player.id).setSkillAlgorithm(new DashSkill());

				else if(powUp.getName().equals("Slow"))
					gameServer.players.get(player.id).setSkillAlgorithm(new SlowAllPlayersSkill());

				else if(powUp.getName().equals("Teleport"))
					gameServer.players.get(player.id).setSkillAlgorithm(new TeleportSkill());




	        	isDead = true;
        	}
        }
            System.out.println("You gain powerup!");
    }
}
