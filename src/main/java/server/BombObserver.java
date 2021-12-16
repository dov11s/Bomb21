package server;

import org.tritonus.share.sampled.convert.TSynchronousFilteredAudioInputStream;
import shared.Vector2f;

import java.util.ArrayList;
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


    public void damagePlayers(List<Vector2f> bLoc){

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

    public void DamgePlayer(MPPlayer p, List<Vector2f> pLoc, List<Vector2f> bLoc){
        for(Vector2f Bxy : bLoc){

            System.out.println("Bombos: " + Bxy.x + " " + Bxy.y);

            for(Vector2f Pxy : pLoc) {
                if(Bxy.isEqual(Pxy)){
                    System.out.println("Nuimk zaidejui hp");
                    p.health -=1;
                    return;
                }
            }
        }

    }





    public void explode(Bomb bomb){

        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;

        List<Vector2f> bLoc = new ArrayList<>();

        for(int x = 0; x < this.gameBoard.gridSize; x++)
        {
            for (int y = 0; y < this.gameBoard.gridSize; y++)
                if (this.gameBoard.objects[x][y] == bomb) {
                    for (int i = 0; i <= bomb.explosionRadius(); i++) {
                        //TODO: Make a sophisticated explosion radius calculation
                        this.gameBoard.ClearTarget(x, y, true);


                        if (i == 0) {
                            this.gameBoard.ClearTarget(x, y,true);
                            bLoc.add(new Vector2f(x, y));


                        } else {
                            if (x + i < this.gameBoard.gridSize && right == 0) {                             
                                this.gameBoard.objects[x + i][y].onDamage();
                                this.gameBoard.objects[x + i][y].explodeAnimation = 30;
                                bLoc.add(new Vector2f(x+i, y));

                                System.out.println("Bomba is kaires");

                                if (this.gameBoard.objects[x + i][y].isDead) {
                                    this.gameBoard.ClearTarget(x + 1, y, true);
                                    right++;

                                }

                            }
                            if (y + i < this.gameBoard.gridSize && up == 0) {
                                this.gameBoard.objects[x][y + i].onDamage();
                                this.gameBoard.objects[x][y + i].explodeAnimation = 30;
                                bLoc.add(new Vector2f(x, y+i));

                                System.out.println("Bomba is apacios");

                                if (this.gameBoard.objects[x][y + i].isDead) {
                                    this.gameBoard.ClearTarget(x, y + 1, true);
                                    up++;

                                }
                            }
                            if (x - i > 0 && left == 0) {                        
                                this.gameBoard.objects[x - i][y].onDamage();
                                this.gameBoard.objects[x - i][y].explodeAnimation = 30;
                                bLoc.add(new Vector2f(x-i, y));
                                System.out.println("Bomba is desines");
                                if (this.gameBoard.objects[x - i][y].isDead) {
                                    this.gameBoard.ClearTarget(x - 1, y, true);
                                    left++;

                                }
                            }
                            if (y - i > 0 && down == 0) {
                                this.gameBoard.objects[x][y - i].onDamage();
                                this.gameBoard.objects[x][y - i].explodeAnimation = 30;
                                bLoc.add(new Vector2f(x, y-i));
                                System.out.println("Bomba is virsaus");
                                if (this.gameBoard.objects[x][y - i].isDead) {
                                    this.gameBoard.ClearTarget(x, y - 1, true);
                                    down++;

                                }
                            }
                        }


                    }

                }
        }

        damagePlayers(bLoc);
    }
}
