package server;

import java.util.List;
import java.util.Stack;

public class BombObserver implements BombObserverInterface {
    private GameBoard gameBoard;
    private List<Bomb> bombs = new Stack<Bomb>();


    public BombObserver(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void update() {
        for(int x = 0; x < this.gameBoard.gridSize; x++) {
            for (int y = 0; y < this.gameBoard.gridSize; y++) {
                if (this.gameBoard.objects[x][y].getClass().equals(Bomb.class))
                {
                   bombs.add((Bomb) this.gameBoard.objects[x][y]);
                }
            }
        }

        for (Bomb bomb: bombs) {
            if(bomb.explosionRadius() > 0){
                explode(bomb);
            }
        }
    }

    public void explode(Bomb bomb){
        for(int x = 0; x < this.gameBoard.gridSize; x++)
        {
            for (int y = 0; y < this.gameBoard.gridSize; y++)
            {
                if (this.gameBoard.objects[x][y] == bomb)
                {
                    for(int i = 0; i < bomb.explosionRadius(); i++) {
                        //TODO: Make a sophisticated explosion radius calculation
                        this.gameBoard.ClearTarget(x, y);
                        if(x + i < this.gameBoard.gridSize) {
                            this.gameBoard.objects[x + i][y].onDamage();
                            if (this.gameBoard.objects[x + i][y].isDead) this.gameBoard.ClearTarget(x + 1, y);
                        }
                        if(y + i < this.gameBoard.gridSize) {
                            this.gameBoard.objects[x][y + i].onDamage();
                            if (this.gameBoard.objects[x][y + i].isDead) this.gameBoard.ClearTarget(x, y + 1);
                        }
                        if(x - i > 0) {
                            this.gameBoard.objects[x - i][y].onDamage();
                            if (this.gameBoard.objects[x - i][y].isDead) this.gameBoard.ClearTarget(x - 1, y);
                        }
                        if(y - i > 0) {
                            this.gameBoard.objects[x][y - i].onDamage();
                            if (this.gameBoard.objects[x][y - i].isDead) this.gameBoard.ClearTarget(x, y - 1);
                        }
                    }
                }
            }
        }
    }
}
