package shared;

public class PacketSendString extends Packet
{
	public String text;
	
	public PacketSendString(String text)
	{
		this.text = text;
	}
	
	public PacketSendString()
	{
		this.text = null;
	}
	
}
