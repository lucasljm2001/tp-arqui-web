package com.arquiweb.backend.services;

import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.models.ItemModel;
import com.arquiweb.backend.models.ItemStatus;

import java.util.ArrayList;
import java.util.List;

public interface ItemService {

    public ArrayList<ItemModel> getItems(Long id);

    public ItemModel createItem(String description, FolderModel folder);

    public ItemModel getItemByDescription(String description);

    public ItemModel getItemById(Long id);


    public ItemModel updateItem(ItemModel item);

    public void deleteItem(ItemModel item);

    public void deleteItemsByFolderName(String name);

    public List<ItemModel> getItemsSorted(String sortBy, String direction);

    public List<ItemModel> getItemsByState(Long folderId,
                                           List<ItemStatus> state,
                                           String sortBy,
                                           String direction);

    public void deleteItemById(Long id);

    public void removeAll();
}
