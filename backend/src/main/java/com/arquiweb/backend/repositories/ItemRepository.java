package com.arquiweb.backend.repositories;

import com.arquiweb.backend.models.ItemModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<ItemModel, Integer> {
    @Query("select i from ItemModel i where folderModel.name = :name")
    List<ItemModel> findItemsByFolderName(String name);

    @Query("select i from ItemModel i where i.description = :description")
    ItemModel getItemByDescription(String description);

    @Query("select i from ItemModel i where i.item_id = :id")
    ItemModel getItemById(int id);

    @Transactional
    @Modifying
    @Query("delete ItemModel i where folderModel.name = :name")
    void deleteItemsByFolderName(String name);

}
