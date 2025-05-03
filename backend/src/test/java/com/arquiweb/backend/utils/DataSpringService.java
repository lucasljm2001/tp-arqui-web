package com.arquiweb.backend.utils;

import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.services.FolderService;
import com.arquiweb.backend.services.ItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DataSpringService {

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Autowired
    private ItemService itemService;

    @Autowired
    private FolderService folderService;

    public void loadData(){
        List<FolderModel> folders = new ArrayList<FolderModel>();
        for (int i = 0; i < 10; i++) {
            folders.add(folderService.createFolder("Folder " + i));
        }

        for (FolderModel folder : folders){
            for (int i = 0; i < 10; i++) {
                itemService.createItem("Item " + i, folder);
            }
        }

        for (FolderModel folder : folders.subList(0, 5)) {
            for (int i = 11; i < 15; i++) {
                itemService.createItem("Item " + i, folder);
            }
        }

    }

    public void cleanAll(){
        List<Map<String, Object>> tables = jdbcTemplate.queryForList("SHOW TABLES");
        List<String> tableNames = tables.stream()
                .map(table -> table.values().iterator().next().toString())
                .toList();

        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");

        tableNames.forEach(tableName -> jdbcTemplate.execute("TRUNCATE TABLE " + tableName));

        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
        itemService.removeAll();
        folderService.removeAll();
    }
}
