package com.arquiweb.backend.repositories;


import com.arquiweb.backend.models.FolderModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FolderRepository extends JpaRepository<FolderModel, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from folders  where name = :name", nativeQuery = true)
    void deleteByName(String name);

    @Query("select f from FolderModel f where f.name = :name")
    FolderModel getFolderByName(String name);


    @Query("SELECT f FROM FolderModel f LEFT JOIN f.items i GROUP BY f ORDER BY COUNT(i) ASC")
    List<FolderModel> findAllOrderByItemCountAsc();

    @Query("SELECT f FROM FolderModel f LEFT JOIN f.items i GROUP BY f ORDER BY COUNT(i) DESC")
    List<FolderModel> findAllOrderByItemCountDesc();

}
