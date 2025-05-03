package com.arquiweb.backend.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity

@Table(name = "items")
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;


    @Getter
    private String description;


    @Setter
    @Getter
    private ItemStatus state;

    @Getter
    private LocalDateTime createdAt;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="folder_id", nullable=true)
    private FolderModel folder;


    public ItemModel() {

    }


    public ItemModel (String description) {
        this.description = description;
        this.state = ItemStatus.TODO;
        this.createdAt = LocalDateTime.now();
    }

    public void changeStatus() {
        if (this.state == ItemStatus.TODO) {
            this.state = ItemStatus.DONE;
        } else {
            this.state = ItemStatus.TODO;
        }
    }

    public  void setDescription(String description) {
        this.description = description;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public String getDescription() {
        return description;
    }

    public ItemStatus getState() {
        return state;
    }

    public void setState(ItemStatus state) {
        this.state = state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public FolderModel getFolder() {
        return folder;
    }

    public void setFolder(FolderModel folder) {
        this.folder = folder;
    }

    public Long getItem_id() {
        return item_id;
    }

}
