package server;

import shared.GameBoard;
import shared.PacketUpdatePlayerPos;

public interface UpdateGameDataDelegate
{
	public void updatePlayer(int id, PacketUpdatePlayerPos player);
	public void addPlayer(MPPlayer player);
	public void removePlayer(int id);
}
