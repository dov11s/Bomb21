package shared;

public class SimplifiedGameBoard
{
	public String[][] objects;
	public int gridSize;
	public int size;
	
	public SimplifiedGameBoard()
	{
		this.objects = new String[0][0];
	}
	
	public SimplifiedGameBoard(int size, int gridSize)
	{
		this.size = size;
		this.gridSize = gridSize;
		this.objects = new String[gridSize][gridSize];
	}
}
