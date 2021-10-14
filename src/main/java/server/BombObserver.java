package server;

public class BombObserver {
    private GameBoard gameBoard;


    public BombObserver(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void explode(Bomb bomb){
        for(int x = 0; x < this.gameBoard.gridSize; x++)
        {
            for (int y = 0; y < this.gameBoard.gridSize; y++)
            {
                if (this.gameBoard.objects[x][y] == bomb)
                {
                    this.gameBoard.objects[x+1][y].onDamage();
                    this.gameBoard.objects[x][y+1].onDamage();
                    this.gameBoard.objects[x-1][y].onDamage();
                    this.gameBoard.objects[x][y-1].onDamage();
                }
            }
        }
    }
}
