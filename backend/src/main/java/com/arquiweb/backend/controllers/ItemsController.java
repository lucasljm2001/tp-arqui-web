package com.arquiweb.backend.controllers;

import com.arquiweb.backend.controllers.dto.CreateItemDTO;
import com.arquiweb.backend.controllers.dto.ItemDTO;
import com.arquiweb.backend.models.Exception.NoExisteElItemException;
import com.arquiweb.backend.models.Exception.NoExisteLaCarpetaException;
import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.models.ItemModel;
import com.arquiweb.backend.models.ItemStatus;
import com.arquiweb.backend.services.FolderService;
import com.arquiweb.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ItemsController {
    @Autowired
    ItemService itemService;
    @Autowired
    FolderService folderService;


    @PostMapping("/folders/{folder}/items")
    public ItemDTO saveItem(@RequestBody CreateItemDTO item, @PathVariable("folder") Long id){
        FolderModel folder = folderService.getFolderById(id);
        return new ItemDTO (this.itemService.createItem(item.getDescription(), folder));
    }

    @PatchMapping("/items/{id}")
    public ItemDTO changeItem(@RequestBody CreateItemDTO item, @PathVariable("id") Long id){

        ItemModel oldItem = this.itemService.getItemById(id);

        if (oldItem == null){
            throw new NoExisteElItemException();
        }

        oldItem.setDescription(item.getDescription());

        this.itemService.updateItem(oldItem);
        return new ItemDTO(oldItem);
    }

    @PatchMapping("/items/{id}/toogle")
    public ItemDTO changeItem(@PathVariable("id") Long id){

        ItemModel oldItem = this.itemService.getItemById(id);

        oldItem.changeStatus();

        this.itemService.updateItem(oldItem);
        return new ItemDTO(oldItem);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable("id") Long id){
        ItemModel item = this.itemService.getItemById(id);
        this.itemService.deleteItem(item);
    }

    @GetMapping("/folders/{folder}/items")
    public List<ItemDTO> getItemsOrdered(@PathVariable("folder") Long id,
                                        @RequestParam(defaultValue = "description") String sortBy,
                                         @RequestParam(defaultValue = "desc") String direction,
                                         @RequestParam(defaultValue = "") String state1,
                                         @RequestParam(defaultValue = "") String state2
    ) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "name"; // Default sorting by name
        }
        List<ItemStatus> status = new ArrayList<>();
        if (!state1.isBlank()){
            status.add(ItemStatus.fromString(state1));
        }
        if (!state2.isBlank()){
            status.add(ItemStatus.fromString(state2));
        }
        if (status.isEmpty()){
            status.add(ItemStatus.TODO);
            status.add(ItemStatus.DONE);
        }
        return itemService.getItemsByState(id, status, sortBy, direction).stream().map(ItemDTO::new).toList();
    }

}
