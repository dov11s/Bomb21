package shared;

public class SimplifiedGameBoard implements Cloneable
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

	public SimplifiedGameBoard copyDeep(){

		try {
			return (SimplifiedGameBoard)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
