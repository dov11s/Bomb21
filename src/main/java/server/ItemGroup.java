package server;


import java.util.ArrayList;
import java.util.Iterator;

public class ItemGroup extends ItemComponent{

    ArrayList itemComponents = new ArrayList();

    String groupName;
    String groupDesc;

    public ItemGroup(String newName, String newDesc){
        groupName = newName;
        groupDesc = newDesc;
    }


    public String getGroupName(){
        return groupName;
    }

    public String getGroupDesc(){
        return groupDesc;
    }

    public void add(ItemComponent newItemComponent){
        itemComponents.add(newItemComponent);
    }

    public void remove(ItemComponent newItemComponent){
        itemComponents.remove(newItemComponent);
    }

    public ItemComponent getComponent(int index){

        return (ItemComponent) itemComponents.get(index);
    }


    public void displayItemInformation(){
        System.out.println(getGroupName() +  "\n" + getGroupDesc() + "\n");


        Iterator itemIterator = itemComponents.iterator();

        while (itemIterator.hasNext()){
            ItemComponent itemInfo = (ItemComponent) itemIterator.next();
            itemInfo.displayItemInformation();
        }

        System.out.println();
        System.out.println();
    }





}
