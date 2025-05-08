package com.arquiweb.backend.controllers;

import com.arquiweb.backend.controllers.dto.CreateFolderDTO;
import com.arquiweb.backend.controllers.dto.DeleteResponseDTO;
import com.arquiweb.backend.controllers.dto.FolderDTO;
import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.models.ItemModel;
import com.arquiweb.backend.services.FolderService;
import com.arquiweb.backend.services.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/folders")
public class FoldersController {
    @Autowired
    FolderService folderService;
    @Autowired
    ItemService itemService;

    @PostMapping()
    public FolderDTO createFolder(@RequestBody CreateFolderDTO folder){
        return new FolderDTO(this.folderService.createFolder(folder.getName()));
    }

    @PatchMapping("/{id}")
    public FolderDTO updateFolder(@PathVariable("id") Long id, @RequestBody CreateFolderDTO folder){
        return new FolderDTO (this.folderService.updateFolder(id, folder.getName()));
    }

    @DeleteMapping("/{id}")
    public DeleteResponseDTO deleteFolder(@PathVariable("id") Long id){
        this.folderService.deleteFolder(id);
        return new DeleteResponseDTO("Carpeta eliminada");
    }

    @GetMapping()
    public List<FolderDTO> getFoldersOrdered(@RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "name"; // Default sorting by name
        } else if (sortBy.equals("itemCount")) {
            return folderService.getFoldersSortedByItemCount(direction).stream().map(FolderDTO::new).toList();
        }
        return folderService.getFoldersSorted(sortBy, direction).stream().map(FolderDTO::new).toList();
    }
}
