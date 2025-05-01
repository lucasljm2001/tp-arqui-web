package com.arquiweb.backend.services;

import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.models.ItemModel;
import com.arquiweb.backend.repositories.FolderRepository;
import com.arquiweb.backend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    FolderRepository folderRepository;

    public ArrayList<ItemModel> getItems(String name){
        return (ArrayList<ItemModel>) itemRepository.findItemsByFolderName(name);
    }

    public ItemModel createItem(ItemModel item, FolderModel folder){
        item.setFolder(folder);
        return itemRepository.save(item);
    }

    public ItemModel getItemByDescription(String description){
        return itemRepository.getItemByDescription(description);
    }

    public ItemModel getItemById(int id){
        return itemRepository.getItemById(id);
    }


    public ItemModel updateItem(ItemModel item){
        return itemRepository.save(item);
    }

    public void deleteItem(ItemModel item){
        itemRepository.delete(item);
    }

    public void deleteItemsByFolderName(String name){
        itemRepository.deleteItemsByFolderName(name);
    }
}
