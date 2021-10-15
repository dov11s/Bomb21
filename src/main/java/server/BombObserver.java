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
                    //TODO: Make a sophisticated explosion radius calculation
                    this.gameBoard.ClearTarget(x, y);
                    this.gameBoard.objects[x+1][y].onDamage();
                    if(this.gameBoard.objects[x+1][y].isDead) this.gameBoard.ClearTarget(x+1, y);
                    this.gameBoard.objects[x][y+1].onDamage();
                    if(this.gameBoard.objects[x][y+1].isDead) this.gameBoard.ClearTarget(x, y+1);
                    this.gameBoard.objects[x-1][y].onDamage();
                    if(this.gameBoard.objects[x-1][y].isDead) this.gameBoard.ClearTarget(x-1, y);
                    this.gameBoard.objects[x][y-1].onDamage();
                    if(this.gameBoard.objects[x][y-1].isDead) this.gameBoard.ClearTarget(x, y-1);
                }
            }
        }
    }
}
