package client;

import shared.Player;
import shared.SimplifiedGameBoard;

public interface UpdateGameDataDelegate
{
	public void updatePlayer(Player player);
	public void addPlayer(int id, Player player);
	public void removePlayer(int id);
	public void updateBoard(SimplifiedGameBoard gameboard);
}
