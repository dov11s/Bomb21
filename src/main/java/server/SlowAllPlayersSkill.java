package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.SimplifiedPlayer;

public class SlowAllPlayersSkill implements SkillAlgorithm
{
	private final int cooldown = 10 * 60; //10 seconds
	private final String name = "Slow";
	private int currentCooldown = 0;
	private final int timer = 4 * 60; //4 seconds
	private int currentTimer = 0;
	protected volatile Map<Integer, SimplifiedPlayer> simplifiedPlayers;
	@Override
	public void useSkill(PlayerInfo p) 
	{
		if (this.currentCooldown == 0)
		{
			GameServer gameserver = GameServer.getInstance();
			simplifiedPlayers = new HashMap<Integer, SimplifiedPlayer>();
    		for(MPPlayer singlePlayer : gameserver.players.values())
    		{
				if (singlePlayer != null)
				{
					if (singlePlayer != p) 
					{
						SimplifiedPlayer player = new SimplifiedPlayer();
						player.id = p.id;
						player.speed = p.speed;
						float newSpeed = p.speed * 0.5f;
						singlePlayer.speed = newSpeed;	
						this.simplifiedPlayers.put(player.id, player);
					}
		            	
				}
	    	  
	      	}
			this.currentTimer = timer;
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
		if (this.currentTimer > 1)
		{
			this.currentTimer--;
		}
		else if (this.currentTimer == 1)
		{
			GameServer gameserver = GameServer.getInstance();
    		for(MPPlayer singlePlayer : gameserver.players.values())
    		{
				if (singlePlayer != null)
				{
					if (singlePlayer != p) 
					{
						SimplifiedPlayer simplifiedPlayer = this.simplifiedPlayers.get(singlePlayer.id);
						if (simplifiedPlayer != null)
						{
							singlePlayer.speed = simplifiedPlayer.speed;	
						}	
					}
		            	
				}
	    	  
	      	}
			this.currentTimer --;
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
