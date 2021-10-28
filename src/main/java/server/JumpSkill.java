package server;

public class JumpSkill implements SkillAlgorithm
{
	private final int cooldown = 5 * 60; //5 seconds
	private final String name = "Jump";
	private int currentCooldown = 0;
	int state = 0;
	float lastSpeed = 0;
	@Override
	public void useSkill(PlayerInfo p) 
	{
		if (this.currentCooldown == 0)
		{
			this.state = 2;
			this.currentCooldown = this.cooldown;
		}
	}

	@Override
	public void onTick(PlayerInfo p)
	{
		if (this.currentCooldown > 0)
		{
			this.currentCooldown --;
		}
		
		if (this.state == 2)
		{
			GameServer gameserver = GameServer.getInstance();
			this.lastSpeed = p.speed;
			p.speed = (gameserver.gameBoard.size / gameserver.gameBoard.gridSize) * 2;
			this.state = 1;
		}
		else if (this.state == 1)
		{
			p.speed = lastSpeed;
			this.state = 0;
		}	
	}

	@Override
	public String getName() 
	{
		return this.name;
	}

	@Override
	public int getCooldown() 
	{
		return this.currentCooldown;
	}
}
