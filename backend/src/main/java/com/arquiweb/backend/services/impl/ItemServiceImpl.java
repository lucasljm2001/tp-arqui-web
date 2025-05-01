package com.arquiweb.backend.services.impl;

import com.arquiweb.backend.models.Exception.NombreVacioException;
import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.models.ItemModel;
import com.arquiweb.backend.models.ItemStatus;
import com.arquiweb.backend.repositories.FolderRepository;
import com.arquiweb.backend.repositories.ItemRepository;
import com.arquiweb.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    FolderRepository folderRepository;

    public ArrayList<ItemModel> getItems(Long id){
        return (ArrayList<ItemModel>) itemRepository.findItemsByFolderId(id);
    }

    public ItemModel createItem(String description, FolderModel folder){
        if (description.isBlank()){
            throw new NombreVacioException();
        }
        ItemModel item = new ItemModel(description);
        item.setFolder(folder);
        folder.addItem(item);
        return itemRepository.save(item);
    }

    public ItemModel getItemByDescription(String description){
        return itemRepository.getItemByDescription(description);
    }

    public ItemModel getItemById(Long id){
        return itemRepository.getItemById(id);
    }


    public ItemModel updateItem(ItemModel item){
        if (item.getDescription().isBlank()){
            throw new NombreVacioException();
        }
        return itemRepository.save(item);
    }

    public void deleteItem(ItemModel item){
        itemRepository.delete(item);
    }

    public void deleteItemsByFolderName(String name){
        itemRepository.deleteItemsByFolderName(name);
    }

    public List<ItemModel> getItemsSorted(String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        return itemRepository.findAll(sort);
    }

    public List<ItemModel> getItemsByState(Long folderId,
                                           List<ItemStatus> state,
                                           String sortBy,
                                           String direction) {
        return itemRepository.findByFolderAndStateSorted(folderId, state.stream().map(Enum::toString).toList(), sortBy, direction);
    }

    @Override
    public void deleteItemById(Long id) {
        ItemModel item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            itemRepository.delete(item);
        }
    }


    @Override
    public void removeAll() {
        itemRepository.deleteAll();
    }

}
