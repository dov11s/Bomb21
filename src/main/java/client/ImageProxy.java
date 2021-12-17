package client;

public class ImageProxy implements ImageFile{

	HeavyImageFile file = null; //heavy image file
	int width;
	int height;
	//HeavyImageFile file;
	
	String path;
	ImageFile parentProxy;
	
	public ImageProxy(String newPath, int width, int height)
	{
		this.path = newPath;
		this.width = width;
		this.height = height;
	}
	
	public ImageProxy(ImageFile anotherProxy){
		parentProxy = anotherProxy;
	}

	@Override
	public void drawImage(int x, int y, float r, float g, float b)
	{
		if(parentProxy == null) 
		{
			if(file == null) 
			{
				file = new HeavyImageFile(path, width, height);
			}
			file.drawImage(x, y, r, g, b);
		}
		else
		{ 
			parentProxy.drawImage(x, y, r, g, b);
		}
		
	}
	

}
