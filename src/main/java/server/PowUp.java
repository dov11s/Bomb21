package server;

public abstract class PowUp {

    private String name;
    private String color;



    public String getName(){
        return name;
    }

    public String getColor(){
        return color;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setColor(String newColor){
        color = newColor;
    }


}
