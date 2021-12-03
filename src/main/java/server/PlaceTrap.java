package server;

import java.util.Random;

public class PlaceTrap extends Expression{
    @Override
    public GameBoard randomly(int quantity, GameBoard board) {

        Random r = new Random();
        int low = 1;
        int high = 20;



        for (int i = 0; i<quantity; i++){
            int x = r.nextInt(high-low) + low;
            int y = r.nextInt(high-low) + low;

            board.addObject(x, y, "trap");

        }


        return board;
    }

    @Override
    public GameBoard linear(int quantity, GameBoard board) {


        for (int i = 0; i<quantity; i++){

            int x = 4 + i*2;
            int y = 9;




            board.addObject(x, y, "trap");

        }


        return board;
    }

    @Override
    public GameBoard zigzag(int quantity, GameBoard board) {

        int reverse = -1;

        for (int i = 0; i<quantity; i++){

            int x = 4 + i;
            int y = 9 + (i * reverse);

            reverse *=-1;



            board.addObject(x, y, "trap");

        }


        return board;
    }
}
