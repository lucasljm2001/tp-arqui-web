package com.arquiweb.backend.controllers;

import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.models.ItemModel;
import com.arquiweb.backend.services.FolderService;
import com.arquiweb.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class ItemsController {
    @Autowired
    ItemService itemService;
    @Autowired
    FolderService folderService;

    @GetMapping("/items/{folder}")
    public ArrayList<ItemModel> getItems(@PathVariable("folder") String folder){
        return itemService.getItems(folder);
    }
    @PostMapping("/{folder}/items")
    public void saveItem(@RequestBody ItemModel item, @PathVariable("folder") String folderName){
        FolderModel folder = folderService.getFolderByName(folderName);
        this.itemService.createItem(item, folder);
    }

    @PutMapping("/items/{itemName}/{action}")
    public ItemModel changeItem(@RequestBody ItemModel item, @PathVariable("itemName") String itemName, 
        @PathVariable("action") String action){
        String state = new String("state");
        ItemModel oldItem = this.itemService.getItemByDescription(itemName);
        if (action.equals(state)) {
            oldItem.setState(item.getState());
        } else{
            oldItem.setDescription(item.getDescription());
        }
        this.itemService.updateItem(oldItem);
        return oldItem;
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable("id") int id){
        ItemModel item = this.itemService.getItemById(id);
        this.itemService.deleteItem(item);
    }

}
