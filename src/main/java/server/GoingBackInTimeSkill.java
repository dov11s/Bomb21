package server;

import shared.SimplifiedPlayer;

public class GoingBackInTimeSkill implements SkillAlgorithm
{
	private final int cooldown = 10 * 60; //10 seconds
	private final String name = "Time travel";
	private int currentCooldown = 0;
	private final int timer = 3 * 60; //3 second
	private int currentTimer = 0;
	private Caretaker careTaker; //Memory
	private boolean skillActive = false;
	@Override
	public void useSkill(PlayerInfo p) 
	{
		
		if (this.currentCooldown == 0)
		{

			GameServer.getInstance().past.increaseCount();

			this.careTaker = new Caretaker();
			p.canDie = false;
			this.currentTimer = timer;
			this.currentCooldown = this.cooldown;
			this.skillActive = true;
		}
	}

	@Override
	public void onTick(PlayerInfo p)
	{
		if (this.currentCooldown > 0)
		{
			this.currentCooldown --;
		}
		if (this.skillActive)
		{
			 
			if (this.currentTimer == 0 || p.getHealth() == 0)
			{
				this.currentTimer = 0;
			}
			else if (this.currentTimer % 10 == 0)
			{
				Memento state = p.saveState();
				careTaker.add(state);
				this.currentTimer --;
			}
			else
			{
				this.currentTimer --;
			}
			
			if (this.currentTimer == 0)
			{
				p.isHoldingDown = false;
				p.isHoldingUp = false;
				p.isHoldingLeft = false;
				p.isHoldingRight = false;
				if (careTaker.size() != 0)
				{
					p.restoreState(careTaker.undo());
				}
				else
				{
					this.skillActive = false;
					p.canDie = true;
				}
			}
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
