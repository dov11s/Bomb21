package Client;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;

import com.esotericsoftware.kryonet.Listener;


public class ClientProgram extends Listener
{
	static long window = -1;
	static Player player = new Player();
	static Network network = new Network();
	static Map<Integer,MPPlayer> players = new HashMap<Integer,MPPlayer>();
	static int length = 640;
	static int height = 480;

	public static void main(String[] args) throws Exception 
	{
		
		if (GLFW.glfwInit() != true)
		{
			System.err.println("err");
		}
		
		window = GLFW.glfwCreateWindow(length,height, "Window", 0, 0);
		
		GLFW.glfwShowWindow(window);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		//Init GL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, length, 0, height, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
////		
//	
		
		network.connect();
		
		while (GLFW.glfwWindowShouldClose(window) != true)
		{
			GLFW.glfwPollEvents();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			
			update();
			render();
			
			if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_TRUE)
			{
				GLFW.glfwSetWindowShouldClose(window, true);
			}
			
		}
	}
	
	public static void update(){
		player.update();
		
		//Update position
		if(player.networkPosition.x != player.position.x){
			//Send the player's X value
			PacketUpdateX packet = new PacketUpdateX();
			packet.x = player.position.x;
			network.client.sendUDP(packet);
			
			player.networkPosition.x = player.position.x;
		}
		if(player.networkPosition.y != player.position.y){
			//Send the player's Y value
			PacketUpdateY packet = new PacketUpdateY();
			packet.y = player.position.y;
			network.client.sendUDP(packet);
			
			player.networkPosition.y = player.position.y;
		}
	}
	
	public static void render(){
		//Render player
		GL11.glColor3f(1,255,1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2f(player.position.x, player.position.y+8);
		GL11.glVertex2f(player.position.x+8, player.position.y-8);
		GL11.glVertex2f(player.position.x-8, player.position.y-8);
		GL11.glEnd();
		
		//Render other players
		GL11.glColor3f(1, 1, 0);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for(MPPlayer mpPlayer : players.values()){
			GL11.glVertex2f(mpPlayer.x, mpPlayer.y+8);
			GL11.glVertex2f(mpPlayer.x+8, mpPlayer.y-8);
			GL11.glVertex2f(mpPlayer.x-8, mpPlayer.y-8);
		}
		GL11.glEnd();
	}
}
