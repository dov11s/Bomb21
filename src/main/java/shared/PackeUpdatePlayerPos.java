package shared;

public class PackeUpdatePlayerPos extends Packet
{

	public Vector2f coordinate;
	
	public PackeUpdatePlayerPos()
	{
		this.coordinate = new Vector2f();
	}
	
	public PackeUpdatePlayerPos(Vector2f coordinate)
	{
		this.coordinate = coordinate;
	}
}
