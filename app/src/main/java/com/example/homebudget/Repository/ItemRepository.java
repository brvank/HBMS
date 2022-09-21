package com.example.homebudget.Repository;

import com.example.homebudget.Model.Item;
import com.example.homebudget.Model.ItemDao;

import java.util.List;

public class ItemRepository {
    private final RoomRepository roomRepository;
    private final ItemDao itemDao;

    public ItemRepository(){
        roomRepository = RoomRepository.getInstance();
        itemDao = roomRepository.getItemDao();
    }

    public List<Item> getItems(){
        return itemDao.getItems();
    }

    public void addItem(Item item){
        itemDao.addItem(item);
    }

    public void deleteItem(Item item){
        itemDao.deleteItem(item);
    }

    public void updateItem(Item item){
        itemDao.updateItem(item);
    }

    public void deleteSelectedItems(List<Integer> ids){
        itemDao.deletedSelectedItems(ids);
    }

    public void deleteItemsWithCategoryId(int categoryId){
        itemDao.deleteItemsWithCategoryId(categoryId);
    }
}
