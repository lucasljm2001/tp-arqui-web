package com.arquiweb.backend.services;

import com.arquiweb.backend.models.FolderModel;

import java.util.ArrayList;
import java.util.List;

public interface FolderService {
    public ArrayList<FolderModel> getFolders();

    public FolderModel createFolder(String folder);

    public void deleteFolder(Long id);

    public FolderModel getFolderById(Long id);
    public List<FolderModel> getFoldersSorted(String sortBy, String direction);

    public List<FolderModel> getFoldersSortedByItemCount(String direction);

    public FolderModel updateFolder(Long id, String newName);

    public void removeAll();
}
