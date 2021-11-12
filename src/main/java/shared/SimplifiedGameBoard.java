package shared;

public class SimplifiedGameBoard
{
	public SimplifiedGameObject[][] objects;
	public int gridSize;
	public int size;
	
	public SimplifiedGameBoard()
	{
		this.objects = new SimplifiedGameObject[0][0];
	}
	
	public SimplifiedGameBoard(int size, int gridSize)
	{
		this.objects = new SimplifiedGameObject[gridSize][gridSize];
		this.size = size;
		this.gridSize = gridSize;
	}



}
