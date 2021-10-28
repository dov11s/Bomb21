package client;

import shared.SimplifiedGameBoard;
import shared.SimplifiedPlayer;

public interface UpdateGameDataDelegate
{
	public void updatePlayer(SimplifiedPlayer player);
	public void addPlayer(int id, SimplifiedPlayer player);
	public void removePlayer(int id);
	public void updateBoard(SimplifiedGameBoard gameboard);
}
