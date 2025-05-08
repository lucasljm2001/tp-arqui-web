package com.arquiweb.backend.services;

import com.arquiweb.backend.models.Exception.NoExisteLaCarpetaException;
import com.arquiweb.backend.models.Exception.NombreVacioException;
import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.repositories.FolderRepository;
import com.arquiweb.backend.services.impl.FolderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FolderServiceTest {

    @Mock
    private FolderRepository folderRepository;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private FolderServiceImpl folderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createFolderWithValidNameShouldCreateFolder() {
        // Arrange
        String folderName = "Test Folder";
        FolderModel expectedFolder = new FolderModel();
        expectedFolder.setName(folderName);
        when(folderRepository.save(any(FolderModel.class))).thenReturn(expectedFolder);

        // Act
        FolderModel result = folderService.createFolder(folderName);

        // Assert
        assertNotNull(result);
        assertEquals(folderName, result.getName());
        verify(folderRepository).save(any(FolderModel.class));
    }

    @Test
    void createFolderWithEmptyNameShouldThrowException() {
        // Arrange
        String emptyName = "";

        // Act & Assert
        assertThrows(NombreVacioException.class, () -> folderService.createFolder(emptyName));
        verify(folderRepository, never()).save(any(FolderModel.class));
    }

    @Test
    void deleteFolderWithValidIdShouldDeleteFolder() {
        // Arrange
        Long folderId = 1L;
        FolderModel folder = new FolderModel();
        folder.setFolder_id(folderId);
        when(folderRepository.existsById(folderId)).thenReturn(true);
        when(folderRepository.getReferenceById(folderId)).thenReturn(folder);

        // Act
        folderService.deleteFolder(folderId);

        // Assert
        verify(folderRepository).deleteById(folderId);
    }

    @Test
    void deleteFolderWithInvalidIdShouldThrowException() {
        // Arrange
        Long invalidId = 999L;
        when(folderRepository.existsById(invalidId)).thenReturn(false);

        // Act & Assert
        assertThrows(NoExisteLaCarpetaException.class, () -> folderService.deleteFolder(invalidId));
        verify(folderRepository, never()).deleteById(any());
    }

    @Test
    void getFolderByIdShouldReturnFolder() {
        // Arrange
        Long folderId = 1L;
        FolderModel expectedFolder = new FolderModel();
        expectedFolder.setFolder_id(folderId);
        when(folderRepository.getReferenceById(folderId)).thenReturn(expectedFolder);

        // Act
        FolderModel result = folderService.getFolderById(folderId);

        // Assert
        assertNotNull(result);
        assertEquals(folderId, result.getFolder_id());
    }

    @Test
    void getFoldersSortedShouldReturnSortedFolders() {
        // Arrange
        String sortBy = "name";
        String direction = "asc";
        List<FolderModel> expectedFolders = Arrays.asList(new FolderModel(), new FolderModel());
        when(folderRepository.findAll(any(Sort.class))).thenReturn(expectedFolders);

        // Act
        List<FolderModel> result = folderService.getFoldersSorted(sortBy, direction);

        // Assert
        assertEquals(expectedFolders.size(), result.size());
        verify(folderRepository).findAll(any(Sort.class));
    }

    @Test
    void getFoldersSortedByItemCountShouldReturnSortedFolders() {
        // Arrange
        String direction = "desc";
        List<FolderModel> expectedFolders = Arrays.asList(new FolderModel(), new FolderModel());
        when(folderRepository.findAllOrderByItemCountDesc()).thenReturn(expectedFolders);

        // Act
        List<FolderModel> result = folderService.getFoldersSortedByItemCount(direction);

        // Assert
        assertEquals(expectedFolders.size(), result.size());
        verify(folderRepository).findAllOrderByItemCountDesc();
    }

    @Test
    void updateFolderWithValidDataShouldUpdateFolder() {
        // Arrange
        Long folderId = 1L;
        String newName = "Updated Folder";
        FolderModel existingFolder = new FolderModel();
        existingFolder.setFolder_id(folderId);
        when(folderRepository.getReferenceById(folderId)).thenReturn(existingFolder);
        when(folderRepository.save(any(FolderModel.class))).thenReturn(existingFolder);

        // Act
        FolderModel result = folderService.updateFolder(folderId, newName);

        // Assert
        assertNotNull(result);
        verify(folderRepository).save(any(FolderModel.class));
    }

    @Test
    void updateFolderWithEmptyNameShouldThrowException() {
        // Arrange
        Long folderId = 1L;
        String emptyName = "";

        // Act & Assert
        assertThrows(NombreVacioException.class, () -> folderService.updateFolder(folderId, emptyName));
        verify(folderRepository, never()).save(any(FolderModel.class));
    }

    @Test
    void removeAllShouldDeleteAllFolders() {
        // Act
        folderService.removeAll();

        // Assert
        verify(folderRepository).deleteAll();
    }
} 