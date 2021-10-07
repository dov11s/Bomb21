package shared;

public class PacketUpdateGameBoard extends Packet
{
	public GameBoard gameBoard;
	
	public PacketUpdateGameBoard(GameBoard gameBoard)
	{
		this.gameBoard = gameBoard;
	}
}
