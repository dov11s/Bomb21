package server;

import shared.Vector2f;

public class Memento {

	int id;
	Vector2f coordinate;
	boolean placedBomb;
	int health;
	int baseHealth;
	float speed;
	float baseSpeed;
	int size;
	int deathCounter;

	
	
	public Memento(	int id,
		Vector2f coordinate,
		boolean placedBomb, int health,
		int baseHealth, float speed, float baseSpeed,
		int size, int bombCount, int bombTimer, int deathCounter) 
	{
		this.id = id;
		this.coordinate = coordinate;
		this.placedBomb = placedBomb;
		this.health = health;
		this.baseHealth = baseHealth;
		this.speed = speed;
		this.baseSpeed = baseSpeed;
		this.size = size;
		this.deathCounter = deathCounter;
	}

	public boolean getState(PlayerInfo org) {
		if(id == org.getId())
		{
			org.setCoordinate(coordinate);
			org.setPlacedBomb(placedBomb);
			org.setHealth(baseHealth);
			org.setBaseHealth(baseHealth);
			org.setSpeed(baseSpeed);
			org.setBaseSpeed(baseSpeed);
			org.setSize(size);
			
			return true;
		}
		return false;
		
	}
	
}

