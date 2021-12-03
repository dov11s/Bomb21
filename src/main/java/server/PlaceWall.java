package server;

import java.util.Random;

public class PlaceWall extends Expression{


    @Override
    public GameBoard randomly(int quantity, GameBoard board) {

        Random r = new Random();
        int low = 1;
        int high = 20;


        for (int i = 0; i<quantity; i++){
            int x = r.nextInt(high-low) + low;
            int y = r.nextInt(high-low) + low;


            board.addObject(x, y, "wall");


        }


        return board;
    }

    @Override
    public GameBoard linear(int quantity, GameBoard board) {





        for (int i = 0; i<quantity; i++){

            int x = 9 + i;
            int y = 9;




            board.addObject(x, y, "wall");

        }


        return board;
    }

    @Override
    public GameBoard zigzag(int quantity, GameBoard board) {


        for (int i = 0; i<quantity; i++){

            int x = 8 + i;
            int y = 8 + i;



            board.addObject(x, y, "wall");


        }


        return board;
    }
}
