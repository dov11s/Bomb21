package server;

import java.util.ArrayList;
import java.util.List;

import shared.Vector2f;

public class StateEndingGame extends GameState
{
	private final int gameTime = 5;
	private int timer = 0;
	private boolean playersDead = false;
	private int row = 0;
	private int column = 1;
	private int row2 = 1;
	private int column2 = 1;
	private int center = 0;
	@Override
	public void handleUpdate() 
	{
		GameServer gameServer = GameServer.getInstance();
		this.isReadyForNextStage = false;
		if (timer > gameTime * 60 || playersDead)
		{
			this.isReadyForNextStage = true;
			this.timer = 0;
			this.playersDead = false;
			row = 0;
			column = 1;
			row2 = 1;
			column2 = 1;
			center = 0;
		}
		else
		{
			if (gameServer.checkIfPlayerDead())
			{
				this.playersDead = true;
			}
			gameServer.enableMovement = true;
			placeDeadlyBricks();			
			this.timer ++;
		}
		
	}

	private void placeDeadlyBricks()
	{
		GameServer gameServer = GameServer.getInstance();
		if (timer % 30 == 0)
		{
			if (row < gameServer.gameBoard.gridSize)
			{
				gameServer.gameBoard.addObject(row, column, "wall");
				this.damagePlayers(new Vector2f(row, column));
				row ++;
			}
			else if(column < gameServer.gameBoard.gridSize)
			{
				gameServer.gameBoard.addObject(row, column, "wall");
				this.damagePlayers(new Vector2f(row, column));
				column ++;
			}
			else if (row2 < gameServer.gameBoard.gridSize)
			{
				gameServer.gameBoard.addObject(gameServer.gameBoard.gridSize - row2 - 1, column, "wall");
				this.damagePlayers(new Vector2f(gameServer.gameBoard.gridSize - row2 - 1, column));
				row2 ++;
			}
			else if(column2 < gameServer.gameBoard.gridSize)
			{
				gameServer.gameBoard.addObject(gameServer.gameBoard.gridSize - row2 - 1, gameServer.gameBoard.gridSize - column2 - 1, "wall");
				this.damagePlayers(new Vector2f(gameServer.gameBoard.gridSize - row2 - 1, gameServer.gameBoard.gridSize - column2 - 1));
				column2 ++;
			}
			else
			{
				center ++;
				row = center;
				column = center + 1;
				row2 = center + 1;
				column2 = center + 1;
			}
		}
	}
	
    private void damagePlayers(Vector2f bLoc){

        GameServer session = GameServer.getInstance();

        int cellSize = session.gameBoard.cellSize();

        System.out.println("Kreipiuosi");


        int kk = 0;

        for(MPPlayer p : session.players.values())
        {
            if(p.coordinate != null){

                if(kk != 0){

                    List<Vector2f> pLoc = new ArrayList<>();

                    int x1 = (int)p.coordinate.x / cellSize;
                    int y1 =  (int)p.coordinate.y / cellSize;

                    int x2 =  (int)(p.coordinate.x + p.size) / cellSize;
                    int y2 =  (int)p.coordinate.y / cellSize;

                    int x3 =  (int)p.coordinate.x / cellSize;
                    int y3 =  (int)(p.coordinate.y + p.size) / cellSize;

                    int x4 =  (int)(p.coordinate.x + p.size) / cellSize;
                    int y4 =  (int)(p.coordinate.y + p.size) / cellSize;

                    Vector2f xy1 = new Vector2f(x1, y1);
                    Vector2f xy2 = new Vector2f(x2, y2);
                    Vector2f xy3 = new Vector2f(x3, y3);
                    Vector2f xy4 = new Vector2f(x4, y4);


                    pLoc.add(xy1);

                    if(!xy1.isEqual(xy2)){
                        pLoc.add(xy2);
                        System.out.println("Pridedu 2");
                    }

                    if(!xy1.isEqual(xy3) && !xy2.isEqual(xy3)){
                        pLoc.add(xy3);
                        System.out.println("Pridedu 3");
                    }

                    if(!xy1.isEqual(xy4) && !xy2.isEqual(xy4) && !xy3.isEqual(xy4)){
                        pLoc.add(xy4);
                        System.out.println("Pridedu 4");
                    }


                    DamgePlayer(p, pLoc, bLoc);

                }

                kk++;

            }
        }

    }
    
    private void DamgePlayer(MPPlayer p, List<Vector2f> pLoc, Vector2f bLoc){

        System.out.println("Bombos: " + bLoc.x + " " + bLoc.y);

        for(Vector2f Pxy : pLoc)
        {
            if(bLoc.isEqual(Pxy))
            {
                System.out.println("Crushed by wall");
                p.health = 0;
                return;
            }
        }

    }
	
}
