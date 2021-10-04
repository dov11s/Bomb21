package Client;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import shared.Player;
import shared.Vector2f;

import org.lwjgl.opengl.GL;



public class GameWindow implements UpdateGameDataDelegate
{
	private long window;
	final private int SCREEN_LENGTH = 640;
	final private int SCREEN_WIDTH = 480;
	private Network network;
	private Player mainPlayer;
	private Map<Integer,Player> players;
	
	public GameWindow()
	{
		//Init Window
		if (!initWindow()) 
		{
			System.err.println("ERROR WHILE INITIATING WINDOW");
			return;
		}
		
		//Init Connection
		if (!initConnection())
		{
			System.err.println("ERROR Connecting to host");
			return;
		}
		
		
		this.mainPlayer = new Player(this.network.client.getID(), new Vector2f(256,256));
		this.players = new HashMap<Integer,Player>();
		runGame();
	}
	
	private boolean initConnection()
	{		
		this.network = new Network();
		
		if (!this.network.connect(this))
		{
			return false;
		}

		return true;
	}
	
	private void runGame()
	{
		while (GLFW.glfwWindowShouldClose(window) != true)
		{
			readKeys();
			updateScreen();
			
		}
	}
	
	private boolean initWindow()
	{
		if (GLFW.glfwInit() != true)
		{
			return false;
		}
		
		window = GLFW.glfwCreateWindow(SCREEN_LENGTH, SCREEN_WIDTH, "Window", 0, 0);
		
		GLFW.glfwShowWindow(window);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		//Init GL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, SCREEN_LENGTH, 0, SCREEN_WIDTH, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		return true;
	}

	
	private void updateScreen()
	{			
		GLFW.glfwPollEvents();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		renderPlayers();
		GLFW.glfwSwapBuffers(window);
	}
	
	private void renderPlayers()
	{
		//Render player
		GL11.glColor3f(1,255,1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2f(this.mainPlayer.coordinate.x, mainPlayer.coordinate.y+8);
		GL11.glVertex2f(this.mainPlayer.coordinate.x+8, mainPlayer.coordinate.y-8);
		GL11.glVertex2f(this.mainPlayer.coordinate.x-8, mainPlayer.coordinate.y-8);
		GL11.glEnd();
		
		//Render other players
		GL11.glColor3f(1, 1, 0);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for(Player mpPlayer : players.values()){
			GL11.glVertex2f(mpPlayer.coordinate.x, mpPlayer.coordinate.y+8);
			GL11.glVertex2f(mpPlayer.coordinate.x+8, mpPlayer.coordinate.y-8);
			GL11.glVertex2f(mpPlayer.coordinate.x-8, mpPlayer.coordinate.y-8);
		}
		GL11.glEnd();
	}
	
	private void readKeys()
	{

		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_W) == GLFW.GLFW_TRUE)
		{
			this.network.sendPacketButtonPressUp();
		}
		
		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_S) == GLFW.GLFW_TRUE)
		{
			this.network.sendPacketButtonPressDown();
		}
		
		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_A) == GLFW.GLFW_TRUE)
		{
			this.network.sendPacketButtonPressLeft();
		}
		
		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_D) == GLFW.GLFW_TRUE)
		{
			this.network.sendPacketButtonPressRight();
		}
		
		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_TRUE)
		{
			GLFW.glfwSetWindowShouldClose(window, true);
		}
	}
	
	@Override
	public void updatePlayer(Player player)
	{
		if (this.mainPlayer.id == player.id)
		{
			this.mainPlayer = player;
		}
		else 
		{
			this.players.put(player.id, player);
		}
		
	}
	@Override
	public void addPlayer(int id, Player player)
	{
		players.put(id, player);
	}

	@Override
	public void removePlayer(int id) 
	{
		players.remove(id);
	}

}