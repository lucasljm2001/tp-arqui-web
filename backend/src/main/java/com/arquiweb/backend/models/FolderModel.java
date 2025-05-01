package com.arquiweb.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folders")
public class FolderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long folder_id;

    @Setter
    @Getter
    private String name;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "folder", orphanRemoval = true)
    @JsonIgnore
    private List<ItemModel> items = new ArrayList<ItemModel>();


    public void addItem(ItemModel item){
        items.add(item);
    }

    public void removeItem(ItemModel item){
        items.remove(item);
    }


    public Long getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(Long folder_id) {
        this.folder_id = folder_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
