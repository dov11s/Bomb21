package client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class HeavyImageFile implements ImageFile {

	Sprite heavySprite;
	String path;
	private TextureLoader	textureLoader;
	
	public HeavyImageFile(String newPath, int width, int height)
	{
		textureLoader = new TextureLoader();
		this.heavySprite = new Sprite(textureLoader, newPath, width, height);
	}

	@Override
	public void drawImage(int x, int y, float r, float g, float b) {
		this.heavySprite.draw(x, y, r, g, b);
		
	}
	
}
