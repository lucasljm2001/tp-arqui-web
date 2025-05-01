package com.arquiweb.backend.models;


import jakarta.persistence.*;

@Entity

@Table(name = "items")
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_id;
    private String description;
    private String state;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="folder_id", nullable=true)
    private FolderModel folderModel;
    public int getId(){
        return item_id;
    }


    public void setId(int id) {
        this.item_id = id;
    }

    public String getState(){
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getDescription(){
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setFolder(FolderModel folder){
        this.folderModel = folder;
    }
}
