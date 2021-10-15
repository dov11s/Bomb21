package server;

import shared.Vector2f;

public class Player 
{
	public int id;
	public Vector2f coordinate;
	public float speed;
	public int size;
	public boolean isHoldingLeft;
	public boolean isHoldingRight;
	public boolean isHoldingUp;
	public boolean isHoldingDown;
	public boolean isHoldingUse;
	public boolean placedBomb = true;
	
	public Player()
	{
		this.coordinate = new Vector2f();
		this.coordinate.x = 400;
		this.coordinate.y = 400;
		this.size = 40;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.speed = 2.5f;
	}
	
	public Player(int id, Vector2f coordinate)
	{
		this.id = id;
		this.coordinate = coordinate;
		this.size = 15;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.isHoldingUse = false;
	}
}
