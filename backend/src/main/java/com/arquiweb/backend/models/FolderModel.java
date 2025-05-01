package com.arquiweb.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folders")
public class FolderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int folder_id;

    private String name;


    public int getId(){
        return folder_id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.folder_id = id;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ItemModel> items = new ArrayList<ItemModel>();

    public void addItem(ItemModel item){
        items.add(item);
    }

    public List<ItemModel> getItems(){
        return this.items;
    }
}
