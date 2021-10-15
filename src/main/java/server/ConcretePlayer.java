package server;

public class ConcretePlayer implements PlayerStats 
{
	int health;
	int bombCount;
	int bombRadius;
	float speed;
	String amplifiers;
	int size;
	
	public ConcretePlayer()
	{
		this.health = 3;
		this.bombCount = 2;
		this.bombRadius = 2;
		this.speed = 2.5f;
		this.amplifiers = "Powerups: ";
		this.size = 40;
	}
	
	@Override
	public float getSpeed()
	{
		return this.speed;
	}
	@Override
	public void takeDamage() 
	{
		this.health -= 1;
	}
	@Override
	public String getAplifierList() 
	{
		return this.amplifiers;
	}
	@Override
	public int getSize() 
	{
		return this.size;
	}
	@Override
	public int getBombRadius() 
	{
		return this.bombRadius;
	}
	@Override
	public int getBombCount() 
	{
		return this.bombCount;
	}
	
	
}
