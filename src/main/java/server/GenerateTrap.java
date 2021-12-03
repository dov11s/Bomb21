package server;

public class GenerateTrap implements Chain{
    private Chain nextInChain;

    @Override
    public void setNextChain(Chain nextChain) {
        nextInChain = nextChain;

    }

    @Override
    public GameBoard result(Task text) {

        GameBoard result = text.getBoard();

        if(text.getWhat().equals("trap")){

            PlaceTrap newWalls = new PlaceTrap();

            switch (text.getWhere()){
                case "randomly": result = newWalls.randomly(text.getQuantity(), text.getBoard());
                    break;

                case "zigzag": result = newWalls.zigzag(text.getQuantity(), text.getBoard());
                    break;

                case  "linear": result = newWalls.linear(text.getQuantity(), text.getBoard());
                    break;

            }

            return  result;



        }else{
            nextInChain.result(text);
        }



        return result;

    }

}
