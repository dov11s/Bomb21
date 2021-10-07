package shared;

public class Vector2f
{
	public float x;
	public float y;
	
	public Vector2f(){}
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean isEqual(Vector2f v)
	{
		return (v.x == this.x) && (v.y == this.y);
	}
}
