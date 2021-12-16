package server;

public class ItemList {

    ItemComponent itemList;

    public ItemList(ItemComponent list){
        itemList = list;
    }

    public void getItemList(){
        itemList.displayItemInformation();
    }

}
