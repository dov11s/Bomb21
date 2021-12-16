package server;

public class Item extends ItemComponent{

    String itemName;
    String itemDes;
    int count;

    public Item(String newName, String newDesc, int count){
        itemName = newName;
        itemDes = newDesc;
        this.count = count;
    }


    public String getItemName(){
        return itemName;
    }

    public String getItemDes(){
        return itemDes;
    }

    public int getCount(){
        return count;
    }

    public void increaseCount(){
        count++;
    }

    public void displayItemInformation(){
        System.out.println(itemName + " Desc: " + itemDes + " Count: " + count);
    }




}
