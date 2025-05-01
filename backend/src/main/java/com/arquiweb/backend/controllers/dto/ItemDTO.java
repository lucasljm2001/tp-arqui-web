package com.arquiweb.backend.controllers.dto;

import com.arquiweb.backend.models.ItemModel;
import com.arquiweb.backend.models.ItemStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ItemDTO {

    private Long item_id;


    @Setter
    @Getter
    private String description;


    @Setter
    @Getter
    private ItemStatus state;

    private LocalDateTime createdAt;

    public ItemDTO(ItemModel itemModel){
        this.item_id = itemModel.getItem_id();
        this.description = itemModel.getDescription();
        this.state = itemModel.getState();
        this.createdAt = itemModel.getCreatedAt();
    }

    public ItemDTO() {
    }

    public Long getItem_id() {
        return item_id;
    }

    public String getDescription() {
        return description;
    }

    public ItemStatus getState() {
        return state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
