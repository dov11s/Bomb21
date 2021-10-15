package server;

public class UsableSkill
{
	int userId;
	SkillAlgorithm usableSkill;
	public UsableSkill(int userId)
	{
		this.userId = userId;
		this.usableSkill = new NoUsableSKill();
	}
	
	public void useSkill()
	{
		this.usableSkill.useSkill();
	}
	
	public String getSkillName()
	{
		return this.usableSkill.getName();
	}
}
