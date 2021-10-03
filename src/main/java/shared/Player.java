package shared;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


public class Player 
{
	public int id;
	public Vector2f coordinate;
	
	public Player()
	{
		this.coordinate = new Vector2f();
		this.coordinate.x = 256;
		this.coordinate.y = 256;
	}
	
	public Player(int id, Vector2f coordinate)
	{
		this.id = id;
		this.coordinate = coordinate;
	}
}
