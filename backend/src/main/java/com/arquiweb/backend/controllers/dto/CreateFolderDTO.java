package com.arquiweb.backend.controllers.dto;

public class CreateFolderDTO {
    private String name;
    public CreateFolderDTO() {
    }

    public CreateFolderDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
