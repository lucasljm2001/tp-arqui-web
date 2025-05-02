package com.arquiweb.backend.services.impl;

import com.arquiweb.backend.models.Exception.NoExisteLaCarpetaException;
import com.arquiweb.backend.models.Exception.NombreVacioException;
import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.repositories.FolderRepository;
import com.arquiweb.backend.services.FolderService;
import com.arquiweb.backend.services.ItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    FolderRepository folderRepository;

    @Autowired
    ItemService itemRepository;

    public ArrayList<FolderModel> getFolders(){
        return (ArrayList<FolderModel>) folderRepository.findAll();
    }

    public FolderModel createFolder(String folder){
        if (folder.isBlank()){
            throw new NombreVacioException();
        }
        FolderModel folderModel = new FolderModel();
        folderModel.setName(folder);
        return folderRepository.save(folderModel);
    }

    @Transactional
    public void deleteFolder(Long id){
        if (!folderRepository.existsById(id)) {
            throw new NoExisteLaCarpetaException();
        }
        FolderModel folderModel = folderRepository.getReferenceById(id);
        folderModel.getItems().forEach(item -> itemRepository.deleteItem(item));
        folderRepository.deleteById(id);
    }

    public FolderModel getFolderById(Long id){
        FolderModel folderModel = folderRepository.getReferenceById(id);
        return folderRepository.getReferenceById(id);
    }

    public List<FolderModel> getFoldersSorted(String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        return folderRepository.findAll(sort);
    }

    public List<FolderModel> getFoldersSortedByItemCount(String direction) {
        if ("desc".equalsIgnoreCase(direction)) {
            return folderRepository.findAllOrderByItemCountDesc();
        } else {
            return folderRepository.findAllOrderByItemCountAsc();
        }
    }

    @Override
    public FolderModel updateFolder(Long id, String newName) {
        if (newName.isBlank()){
            throw new NombreVacioException();
        }
        FolderModel folder = folderRepository.getReferenceById(id);
        if (folder != null) {
            folder.setName(newName);
            return folderRepository.save(folder);
        }
        throw new NoExisteLaCarpetaException();
    }

    @Override
    public void removeAll() {
        folderRepository.deleteAll();
    }


}
