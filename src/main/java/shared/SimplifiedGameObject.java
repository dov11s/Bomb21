package shared;

public class SimplifiedGameObject 
{
	public String color;
	public ObjectType type;
	public boolean explodeAnimation;
	public SimplifiedGameObject() {}
	public SimplifiedGameObject(String color, ObjectType type, boolean explodeAnimation)
	{
		this.color = color;
		this.type = type;
		this.explodeAnimation = explodeAnimation; 
	}
}
