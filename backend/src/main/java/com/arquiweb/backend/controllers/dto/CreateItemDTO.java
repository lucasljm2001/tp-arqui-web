package com.arquiweb.backend.controllers.dto;

import lombok.Getter;

public class CreateItemDTO {

    private String description;

    public CreateItemDTO(String description) {
        this.description = description;
    }

    public CreateItemDTO() {
    }


    public String getDescription() {
        return description;
    }
}
