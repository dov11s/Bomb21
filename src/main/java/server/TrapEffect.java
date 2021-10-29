package server;

public interface TrapEffect 
{
	void activateTrap(PlayerInfo p);
	void onTick();
	boolean isDone();
}
