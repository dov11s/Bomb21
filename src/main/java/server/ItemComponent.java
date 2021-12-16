package server;

public abstract class ItemComponent {

    public void add(ItemComponent newItemComponent){
        throw  new UnsupportedOperationException();
    }

    public void remove(ItemComponent newItemComponent){
        throw  new UnsupportedOperationException();
    }

    public ItemComponent getComponent(int index){
        throw  new UnsupportedOperationException();
    }

    public String getItemName(){
        throw  new UnsupportedOperationException();
    }

    public int getItemCount(){
        throw  new UnsupportedOperationException();
    }

    public void displayItemInformation(){
        throw  new UnsupportedOperationException();
    }



}
