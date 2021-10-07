package shared;

public class PacketUpdateGameBoard extends Packet
{
	GameBoard gameboard;
	
	public PacketUpdateGameBoard(GameBoard gameBoard)
	{
		this.gameboard = gameboard;
	}
}
