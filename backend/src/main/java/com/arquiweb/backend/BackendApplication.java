package com.arquiweb.backend;

import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.services.FolderService;
import com.arquiweb.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BackendApplication {

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Autowired
    private ItemService itemService;

    @Autowired
    private FolderService folderService;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
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

    @EventListener
    public void onApplicationEvent(ContextClosedEvent event) {
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
