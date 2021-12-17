package client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import shared.SimplifiedPlayer;


public class ProxyTest {

	public static void main(String[] args) {
		if (GLFW.glfwInit() != true)
		{
			return;
		}
		
		long window;
		int SCREEN_LENGTH = 1000;
		int SCREEN_WIDTH = 1000;
		//Init Window
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
		
		
		
		String path = "Test.jpg";
		
		System.out.println("=== Single Image ===");		
		long startP = System.currentTimeMillis();	
		TextureLoader loader = new TextureLoader();
		Sprite newsprite = new Sprite(loader,path,50,50);
	
		long stopP = System.currentTimeMillis();
		
		System.out.println("One image loaded in: " + (stopP - startP) + " ms");
	
		System.gc(); //		Runtime.getRuntime().gc();
		
		System.out.println("\n=== Heavy Images ===");	
		long startReal = System.currentTimeMillis();
		ImageFile h1 = new HeavyImageFile(path,50,50);
		ImageFile h2 = new HeavyImageFile(path,50,50);
		ImageFile h3 = new HeavyImageFile(path,50,50);
		h2.drawImage(0, 0, 1,1,1);
		long stopReal = System.currentTimeMillis();
		System.out.println("Real loaded in " + (stopReal - startReal) + " ms");	
		
		System.gc(); //		Runtime.getRuntime().gc();

		System.out.println("\n=== Proxy Images - show one image only ===");
		long startProxy = System.currentTimeMillis();
		ImageFile p1 = new ImageProxy(path,50,50);
		ImageFile p2 = new ImageProxy(path,50,50);
		ImageFile p3 = new ImageProxy(path,50,50);
		p2.drawImage(0, 0, 1,1,1);
		long stopProxy = System.currentTimeMillis();
		System.out.println("Proxy loaded in " + (stopProxy - startProxy) + "ms");
		
		System.gc(); //		Runtime.getRuntime().gc();
		
		System.out.println("\n=== Proxy Images - run all ===");
		long startProxyA = System.currentTimeMillis();
		ImageFile a1 = new ImageProxy(path,50,50);
		ImageFile a2 = new ImageProxy(path,50,50);
		ImageFile a3 = new ImageProxy(path,50,50);
		a1.drawImage(0, 0, 1,1,1);;
		a2.drawImage(0, 0, 1,1,1);;
		a3.drawImage(0, 0, 1,1,1);;
		long stopProxyA = System.currentTimeMillis();
		System.out.println("Proxy loaded in " + (stopProxyA - startProxyA) + "ms");
	}

}
