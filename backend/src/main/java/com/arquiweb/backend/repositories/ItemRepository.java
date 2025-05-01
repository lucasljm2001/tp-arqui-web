package com.arquiweb.backend.repositories;

import com.arquiweb.backend.models.ItemModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long> {
    @Query("select i from ItemModel i where i.folder.folder_id = :id")
    List<ItemModel> findItemsByFolderId(Long id);

    @Query("select i from ItemModel i where i.description = :description")
    ItemModel getItemByDescription(String description);

    @Query("select i from ItemModel i where i.item_id = :id")
    ItemModel getItemById(Long id);

    @Transactional
    @Modifying
    @Query("delete ItemModel i where i.folder.name = :name")
    void deleteItemsByFolderName(String name);


    @Query("SELECT i FROM ItemModel i WHERE i.folder.folder_id = :folderId AND i.state in :state ORDER BY " +
            "CASE WHEN :direction = 'asc' THEN " +
            "   CASE WHEN :sortBy = 'description' THEN i.description " +
            "   WHEN :sortBy = 'createdat' THEN i.createdAt END " +
            "END ASC, " +
            "CASE WHEN :direction = 'desc' THEN " +
            "   CASE WHEN :sortBy = 'description' THEN i.description  " +
            "   WHEN :sortBy = 'createdat' THEN i.createdAt END " +
            "END DESC")
    List<ItemModel> findByFolderAndStateSorted(
            @Param("folderId") Long folderId,
            @Param("state") List<String> state,
            @Param("sortBy") String sortBy,
            @Param("direction") String direction
    );

}
