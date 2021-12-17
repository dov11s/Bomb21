package client;

public class ImageCharacterProxy implements ImageFile{

	ImageFile file; 
	
	String path;
	
	boolean isMainCharacter = false;
	String user = null;
	int width;
	int height;
	ImageFile parentProxy;
	
	public ImageCharacterProxy(boolean isMainCharacter, String newPath, int width, int height)
	{
		this.isMainCharacter = isMainCharacter;
		this.path = newPath;
		this.width = width;
		this.height = height;
	}

	public ImageCharacterProxy(ImageFile anotherProxy, boolean isMainCharacter){
		parentProxy = anotherProxy;
		this.isMainCharacter = isMainCharacter;
	}

	
	@Override
	public void drawImage(int x, int y, float r, float g, float b) 
	{
		if(parentProxy == null) 
		{
			if(file == null) 
			{
				file = new HeavyImageFile(this.path, this.width, this.height);
				this.draw(x, y, y, g, b, file);
			}
			else
			{
				this.draw(x, y, y, g, b, file);
			}
			
		}
		else
		{
			this.draw(x, y, y, g, b, parentProxy);
		}
	}
	
	private void draw(int x, int y, int r, float g, float b, ImageFile f)
	{

		if(isMainCharacter)
		{
			//Removing color for main char
			f.drawImage(x, y, 1f, 1f, 1f);
		}
		else
		{
			f.drawImage(x, y, r, g, b);
			float size = 40;
			float xEn = x*4/size;
			float yEn = y*4/size;
			Text.drawString("enemy",xEn- 8/10 ,yEn-1 , size, 2);
		}
	}
}


