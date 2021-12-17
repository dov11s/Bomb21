package client;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import shared.SimplifiedGameBoard;
import shared.SimplifiedPlayer;
import shared.Vector2f;
import shared.ObjectType;

import org.lwjgl.opengl.GL;



public class GameWindow implements UpdateGameDataDelegate
{
	private long window;
	final private int SCREEN_LENGTH = 1000;
	final private int SCREEN_WIDTH = 1000;
	private SimplifiedGameBoard board;
	private Network network;
	private SimplifiedPlayer mainPlayer;
	private Map<Integer,ClientPlayer> players;
	private String displayString = null;
	//Textures
	private ImageFile				bombSprite;
	private ImageFile				wallSprite;
	private ImageFile				groundSprite;
	private ImageFile				powerupSprite;
	private ImageFile				trapSprite;
	private ImageFile				fireSprite;
	private ImageFile				playerSprite;
	
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
		this.players = new HashMap<Integer,ClientPlayer>();
		runGame();
	}


	private void initProxies(int size)
	{
		bombSprite = new ImageProxy("Tnt.png", size, size);
		wallSprite = new ImageProxy("Cobble.png",size,size);
		groundSprite =  new ImageProxy("Grass.png",size,size);
		powerupSprite = new ImageProxy("Powerup.png",size,size);
		trapSprite = new ImageProxy("Trap.png", size,size);
		fireSprite = new ImageProxy("Fire.png",size,size);
		playerSprite = new ImageProxy("Player.jpg", 40, 40);
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
		// enable textures since we're going to use these for our sprites
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthFunc(GL11.GL_ALWAYS);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// disable the OpenGL depth test since we're rendering 2D graphics
		GL11.glEnable(GL11.GL_DEPTH_TEST);
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
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		renderObjects(board);
		renderPlayers();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		renderText();
		GLFW.glfwSwapBuffers(window);
	}

	private void renderText() 
	{
		Text.drawString("Spell " + this.mainPlayer.skillName + "  Cooldown " + (int)(this.mainPlayer.skillCooldown / 60), 5, 2, 50, 2);
		Text.drawString("Player" + this.mainPlayer.id + " Health " + this.mainPlayer.health, 40, 2, 50, 2);

		int size = 40;



		if(this.mainPlayer.health < 1){

			Text.drawString("GG", 2, 6, 100, 20);
		}

		for(SimplifiedPlayer mpPlayer : players.values()) 
		{

			if(this.mainPlayer.id != mpPlayer.id){

				if (mpPlayer.coordinate != null)
				{
					float x = mpPlayer.coordinate.x*4/size;
					float y = mpPlayer.coordinate.y*4/size;

				}

			}

		}
		
		if (this.displayString != null)
		{
			Text.drawString(this.displayString, 10, 40, 55, 4);
		}


	}
	
	//bad design
	private void waitFrame()
	{
		try 
		{
			//around 120 tiems a second
			Thread.sleep(1L);
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void renderPlayers()
	{
		for(ClientPlayer mpPlayer : players.values()){
			mpPlayer.getSprite().drawImage((int)mpPlayer.coordinate.x, (int)mpPlayer.coordinate.y, 0f,(float)(255/255),(float)(127/255));
		}
	}


	private void renderObjects(SimplifiedGameBoard board)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
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
				ImageProxy drawer;
				switch(board.objects[i][j].type)
				{
					default:
					case GROUND:
						drawer = new ImageProxy(groundSprite);
					break;
					case WALL:
						drawer = new ImageProxy(wallSprite);
					break;
					case TRAP:
						drawer = new ImageProxy(trapSprite);
					break;
					case POWERUP:
						drawer = new ImageProxy(powerupSprite);
					break;
					case BOMB:
						drawer = new ImageProxy(bombSprite);
					break;
				}
			
				drawer.drawImage(i*sizeX, j*sizeY, red, green, blue);
				GL11.glDisable(GL11.GL_BLEND);
				if (board.objects[i][j].explodeAnimation == true)
				{
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					drawer = new ImageProxy(fireSprite);
					drawer.drawImage(i*sizeX, j*sizeY, 1, 1, 1);
				}
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
			ClientPlayer clientPlayer = new ClientPlayer(player, playerSprite, true);
			this.mainPlayer = player;
			this.players.put(player.id, clientPlayer);
		}
		else 
		{
			ClientPlayer clientPlayer = new ClientPlayer(player, playerSprite, false);
			this.players.put(player.id, clientPlayer);
		}
		
	}
	@Override
	public void addPlayer(int id, SimplifiedPlayer player)
	{
		if (player.id == mainPlayer.id)
		{
			ClientPlayer clientPlayer = new ClientPlayer(player, playerSprite, true);
			players.put(id, clientPlayer);
		}
		else
		{
			ClientPlayer clientPlayer = new ClientPlayer(player, playerSprite, false);
			players.put(id, clientPlayer);
		}
	}

	@Override
	public void removePlayer(int id) 
	{
		players.remove(id);
	}

	@Override
	public void updateBoard(SimplifiedGameBoard gameboard)
	{
		if ( this.board == null)
		{
			this.initProxies(gameboard.size /gameboard.gridSize);
		}
		this.board = gameboard;
		
	}


	@Override
	public void updateDisplayString(String text) 
	{
		this.displayString = text;
		
	}

}