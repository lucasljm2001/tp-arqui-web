package com.arquiweb.backend.controllers.dto;

import com.arquiweb.backend.models.FolderModel;

public class FolderDTO {
    private String name;
    private Long folder_id;
    private int itemCount;

    public FolderDTO(FolderModel folderModel) {
        this.name = folderModel.getName();
        this.folder_id = folderModel.getFolder_id();
        this.itemCount = folderModel.getItems().size();
    }

    public FolderDTO() {
    }

    public String getName() {
        return name;
    }

    public Long getFolder_id() {
        return folder_id;
    }

    public int getItemCount() {
        return itemCount;
    }
}
