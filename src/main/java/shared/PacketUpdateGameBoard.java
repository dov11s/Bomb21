package shared;

public class PacketUpdateGameBoard extends Packet
{
	public SimplifiedGameBoard gameBoard;
	
	public PacketUpdateGameBoard()
	{
		this.gameBoard = new SimplifiedGameBoard();
	}
	public PacketUpdateGameBoard(SimplifiedGameBoard gameBoard)
	{
		this.gameBoard = gameBoard;
	}
}
