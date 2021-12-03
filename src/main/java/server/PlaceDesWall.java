package server;

import java.util.Random;

public class PlaceDesWall extends Expression{
    @Override
    public GameBoard randomly(int quantity, GameBoard board) {

        Random r = new Random();
        int low = 1;
        int high = 19;


        for (int i = 0; i<quantity; i++){
            int x = r.nextInt(high-low) + low;
            int y = r.nextInt(high-low) + low;

            board.addObject(x, y, "desWall");

        }


        return board;
    }

    @Override
    public GameBoard linear(int quantity, GameBoard board) {



        for (int i = 0; i<quantity; i++){

            int x = 9;
            int y = 9 + i;




            board.addObject(x, y, "desWall");

        }


        return board;
    }

    @Override
    public GameBoard zigzag(int quantity, GameBoard board) {


        int kiekis = quantity;





        for (int i = 1; i<20; i++){

            int x = i;
            int y = i;


            if(kiekis != 0){

                kiekis --;
            }else
            {
                board.addObject(x, y, "desWall");
            }



        }


        return board;
    }
}
