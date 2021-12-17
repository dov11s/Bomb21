package client;

import shared.PacketUpdatePlayerPos;
import shared.SimplifiedPlayer;

public class ClientPlayer extends SimplifiedPlayer
{
	private ImageFile 				playerSprite;
	
	public ClientPlayer (SimplifiedPlayer player, ImageFile texture, boolean isMain)
	{
		super(player);
		playerSprite = new ImageCharacterProxy(texture, isMain);
	}

	public ImageFile getSprite()
	{
		return this.playerSprite;
	}
}
