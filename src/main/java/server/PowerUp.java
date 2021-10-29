package server;

public class PowerUp extends GameObject
{
    private int Timer;
    public PowerUp(String color, float alpha, int timer){
        super(color, alpha);
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
	        	if (gameServer.players.get(player.id).getSkillAlgorithm() instanceof DashSkill)
	        	{
	            	gameServer.players.get(player.id).setSkillAlgorithm(new TeleportSkill());
	        	}
	        	else if (gameServer.players.get(player.id).getSkillAlgorithm() instanceof TeleportSkill)
	        	{
	            	gameServer.players.get(player.id).setSkillAlgorithm(new JumpSkill());
	        	}
	        	else if (gameServer.players.get(player.id).getSkillAlgorithm() instanceof JumpSkill)
	        	{
	            	gameServer.players.get(player.id).setSkillAlgorithm(new SlowAllPlayersSkill());
	        	}
	        	else if (gameServer.players.get(player.id).getSkillAlgorithm() instanceof SlowAllPlayersSkill)
	        	{
	            	gameServer.players.get(player.id).setSkillAlgorithm(new DashSkill());
	        	}
	        	isDead = true;
        	}
        }
            System.out.println("You gain powerup!");
    }
}
