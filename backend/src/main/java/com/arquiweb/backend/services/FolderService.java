package com.arquiweb.backend.services;

import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FolderService {
    @Autowired
    FolderRepository folderRepository;

    public ArrayList<FolderModel> getFolders(){
        return (ArrayList<FolderModel>) folderRepository.findAll();
    }

    public FolderModel createFolder(FolderModel folder){
        return folderRepository.save(folder);
    }

    public void deleteFolder(String name){
        folderRepository.deleteByName(name);
    }

    public FolderModel getFolderByName(String name){
        return folderRepository.getFolderByName(name);
    }
}
