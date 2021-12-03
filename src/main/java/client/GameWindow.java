package client;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import shared.SimplifiedGameBoard;
import shared.SimplifiedPlayer;
import shared.Vector2f;

import org.lwjgl.opengl.GL;



public class GameWindow implements UpdateGameDataDelegate
{
	private long window;
	final private int SCREEN_LENGTH = 1000;
	final private int SCREEN_WIDTH = 1000;
	private SimplifiedGameBoard board;
	private Network network;
	private SimplifiedPlayer mainPlayer;
	private Map<Integer,SimplifiedPlayer> players;



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
		
		
		this.mainPlayer = new SimplifiedPlayer(this.network.client.getID());
		this.players = new HashMap<Integer,SimplifiedPlayer>();
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
			waitFrame();
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
		renderObjects(board);
		renderPlayers();
		renderText();
		GLFW.glfwSwapBuffers(window);
	}

	private void renderText() 
	{
		Text.drawString("Spell " + this.mainPlayer.skillName + "  Cooldown " + (int)(this.mainPlayer.skillCooldown / 60), 5, 2, 50, 2);
		Text.drawString("Health " + this.mainPlayer.health, 60, 2, 50, 2);

		int size = 40;



		if(this.mainPlayer.health < 1){

			Text.drawString("GG", 2, 6, 100, 20);
		}

		for(SimplifiedPlayer mpPlayer : players.values()) {

			if(this.mainPlayer.id != mpPlayer.id){

				if (mpPlayer.coordinate != null)
				{
					float x = mpPlayer.coordinate.x*4/size;
					float y = mpPlayer.coordinate.y*4/size;

					Text.drawString("enemy",x- 8/10 ,y-1 , size, 2);
				}



			}

		}


	}
	
	//bad design
	private void waitFrame()
	{
		try 
		{
			//around 120 tiems a second
			Thread.sleep(8L);
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void renderPlayers()
	{
		//Render player
		GL11.glColor3f(0f,(float)(255/255),(float)(127/255));
//		GL11.glBegin(GL11.GL_TRIANGLES);
//		GL11.glVertex2f(this.mainPlayer.coordinate.x, mainPlayer.coordinate.y+this.mainPlayer.size);
//		GL11.glVertex2f(this.mainPlayer.coordinate.x+this.mainPlayer.size, mainPlayer.coordinate.y-this.mainPlayer.size);
//		GL11.glVertex2f(this.mainPlayer.coordinate.x-this.mainPlayer.size, mainPlayer.coordinate.y-this.mainPlayer.size);
//		GL11.glEnd();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(this.mainPlayer.coordinate.x, this.mainPlayer.coordinate.y);
		GL11.glVertex2f( this.mainPlayer.coordinate.x + this.mainPlayer.size, this.mainPlayer.coordinate.y);
		GL11.glVertex2f(this.mainPlayer.coordinate.x + this.mainPlayer.size, this.mainPlayer.coordinate.y + this.mainPlayer.size);
		GL11.glVertex2f(this.mainPlayer.coordinate.x, this.mainPlayer.coordinate.y + this.mainPlayer.size);
		GL11.glEnd();


		//Render other players
		//GL11.glColor3f(1, 1, 0);

		GL11.glColor3f(0.98f,0.5f,0.447f);
		//GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glBegin(GL11.GL_QUADS);
		for(SimplifiedPlayer mpPlayer : players.values()){

			if (mpPlayer.coordinate != null)
			{
				GL11.glVertex2f(mpPlayer.coordinate.x, mpPlayer.coordinate.y);
				GL11.glVertex2f(mpPlayer.coordinate.x + mpPlayer.size, mpPlayer.coordinate.y);
				GL11.glVertex2f(mpPlayer.coordinate.x + mpPlayer.size, mpPlayer.coordinate.y + mpPlayer.size);
				GL11.glVertex2f(mpPlayer.coordinate.x, mpPlayer.coordinate.y + mpPlayer.size);
	
	//			GL11.glVertex2f(mpPlayer.coordinate.x, mpPlayer.coordinate.y+mpPlayer.size);
	//			GL11.glVertex2f(mpPlayer.coordinate.x+mpPlayer.size, mpPlayer.coordinate.y-mpPlayer.size);
	//			GL11.glVertex2f(mpPlayer.coordinate.x-mpPlayer.size, mpPlayer.coordinate.y-mpPlayer.size);
			}
		}
		GL11.glEnd();
	}


	private void renderObjects(SimplifiedGameBoard board)
	{
		if (board == null)
		{
			return;
		}
		int sizeX = SCREEN_LENGTH / board.gridSize;
		int sizeY = SCREEN_WIDTH / board.gridSize;


		for (int i = 0; i < board.gridSize; i++) {
			for (int j = 0; j < board.gridSize; j++) {
				float red = (float)Integer.valueOf(board.objects[i][j].color.substring(1,3), 16)/255;
				float green = (float)Integer.valueOf(board.objects[i][j].color.substring(3,5), 16)/255;
				float blue = (float)Integer.valueOf(board.objects[i][j].color.substring(5,7), 16)/255;




				GL11.glBegin(GL11.GL_QUADS);

					GL11.glColor3f(red,green,blue);

					GL11.glVertex2f(i*sizeX, j*sizeY);
					GL11.glVertex2f(i*sizeX + sizeX-2, j*sizeY);
					GL11.glVertex2f(i*sizeX + sizeX-2, j*sizeY + sizeY-2);
					GL11.glVertex2f(i*sizeX, j*sizeY + sizeY-2);

				GL11.glEnd();
			}
		}



	}


	
	private void readKeys()
	{
		
		//TODO should read from events instead of polling
		//https://www.glfw.org/docs/3.3/input_guide.html




		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_W) == GLFW.GLFW_TRUE)
		{
			if (!this.mainPlayer.isHoldingUp)
			{
				this.network.sendPacketButtonPressUp();
			}
		}
		else
		{
			if (this.mainPlayer.isHoldingUp)
			{
				this.network.sendPacketButtonReleaseUp();
			}
			
		}
		
		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_S) == GLFW.GLFW_TRUE)
		{
			if (!this.mainPlayer.isHoldingDown)
			{
				this.network.sendPacketButtonPressDown();
			}
		}
		else
		{
			if (this.mainPlayer.isHoldingDown)
			{
				this.network.sendPacketButtonReleaseDown();
			}
			
		}
		
		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_A) == GLFW.GLFW_TRUE)
		{
			if (!this.mainPlayer.isHoldingLeft)
			{
				this.network.sendPacketButtonPressLeft();
			}
		}
		else
		{
			if (this.mainPlayer.isHoldingLeft)
			{
				this.network.sendPacketButtonReleaseLeft();
			}
			
		}
		
		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_D) == GLFW.GLFW_TRUE)
		{
			if (!this.mainPlayer.isHoldingRight)
			{
				this.network.sendPacketButtonPressRight();
			}
		}
		else
		{
			if (this.mainPlayer.isHoldingRight)
			{
				this.network.sendPacketButtonReleaseRight();
			}
			
		}


		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_E) == GLFW.GLFW_TRUE)
		{
			if (!this.mainPlayer.isHoldingUse)
			{
				this.network.sendPacketButtonPressUse();
			}
		}
		else
		{
			if (this.mainPlayer.isHoldingUse)
			{
				this.network.sendPacketButtonReleaseUse();
			}
		}
		
		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_TRUE)
		{
			if (!this.mainPlayer.isHoldingSkill)
		{
				this.network.sendPacketButtonPressSkill();
			}
		}
		else
		{
			if (this.mainPlayer.isHoldingSkill)
			{
				this.network.sendPacketButtonReleaseSkill();
			}
		}

		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_TRUE)
		{
			GLFW.glfwSetWindowShouldClose(window, true);
		}
	}
	
	@Override
	public void updatePlayer(SimplifiedPlayer player)
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
	public void addPlayer(int id, SimplifiedPlayer player)
	{
		players.put(id, player);
	}

	@Override
	public void removePlayer(int id) 
	{
		players.remove(id);
	}

	@Override
	public void updateBoard(SimplifiedGameBoard gameboard)
	{
		this.board = gameboard;
		
	}

}