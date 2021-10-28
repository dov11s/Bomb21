package server;

public interface SkillAlgorithm 
{
	int getCooldown();
	void useSkill(PlayerInfo p);
	void onTick(PlayerInfo p);
	String getName();
}

