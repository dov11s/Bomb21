package server;

import java.util.Locale;

public class ConversionContext {

    private String givenSentence = "";

    private String result = "";

    private String what = "";
    private String where = "";

    private int quantity = 0;

    String[] parts;

    public ConversionContext(String sentence){

        givenSentence = sentence.toLowerCase();

        parts = getSentence().split(" ");

        what = parts[2];

        quantity = Integer.parseInt(parts[1]);

        where = parts[3];

        result = givenSentence + " ";

    }

    public String getSentence(){
        return givenSentence;
    }

    public String getWhat(){
        return what;
    }

    public String getWhere(){
        return where;
    }

    public String getResult(){
        return result;
    }

    public int getQuantity(){
        return  quantity;
    }



}
