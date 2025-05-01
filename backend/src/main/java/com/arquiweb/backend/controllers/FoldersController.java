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
@RequestMapping("/folders")
public class FoldersController {
    @Autowired
    FolderService folderService;
    @Autowired
    ItemService itemService;

    @GetMapping()
    public ArrayList<FolderModel> getFolders(){
        return folderService.getFolders();
    }

    @GetMapping("/{name}")
    public ArrayList<ItemModel> getFolderId(@PathVariable("name") String name){
        return this.itemService.getItems(name);
    }

    @PostMapping()
    public FolderModel createFolder(@RequestBody FolderModel folder){
        return this.folderService.createFolder(folder);
    }

    @DeleteMapping("/{name}")
    public void deleteFolder(@PathVariable("name") String name){
        ArrayList<ItemModel> items = this.itemService.getItems(name);
        for (ItemModel item : items) {
            this.itemService.deleteItem(item);
        }
        this.folderService.deleteFolder(name);
    }

}
