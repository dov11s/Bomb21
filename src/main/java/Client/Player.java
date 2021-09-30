package Client;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


public class Player 
{

	float speed = 0.1f;
	Vector2f position = new Vector2f(256,256);
	Vector2f networkPosition = new Vector2f(0,0);
	
	public void update()
	{
		if(GLFW.glfwGetKey(ClientProgram.window, GLFW.GLFW_KEY_W) == GLFW.GLFW_TRUE)
		{
			position.y += speed;
		}
		
		if(GLFW.glfwGetKey(ClientProgram.window, GLFW.GLFW_KEY_S) == GLFW.GLFW_TRUE)
		{
			position.y -= speed;
		}
		
		if(GLFW.glfwGetKey(ClientProgram.window, GLFW.GLFW_KEY_A) == GLFW.GLFW_TRUE)
		{
			position.x -= speed;
		}
		
		if(GLFW.glfwGetKey(ClientProgram.window, GLFW.GLFW_KEY_D) == GLFW.GLFW_TRUE)
		{
			position.x += speed;
		}
	}
	
	public class Vector2f
	{
		public float x;
		public float y;
		
		public Vector2f(float x, float y)
		{
			this.x = x;
			this.y = y;
		}
	}
}
