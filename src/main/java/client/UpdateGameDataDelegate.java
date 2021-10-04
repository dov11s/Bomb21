package client;

import shared.Player;

public interface UpdateGameDataDelegate
{
	public void updatePlayer(Player player);
	public void addPlayer(int id, Player player);
	public void removePlayer(int id);
}
