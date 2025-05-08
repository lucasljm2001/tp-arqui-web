package com.arquiweb.backend.services;

import com.arquiweb.backend.models.Exception.NoExisteElItemException;
import com.arquiweb.backend.models.Exception.NombreVacioException;
import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.models.ItemModel;
import com.arquiweb.backend.models.ItemStatus;
import com.arquiweb.backend.repositories.FolderRepository;
import com.arquiweb.backend.repositories.ItemRepository;
import com.arquiweb.backend.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private FolderRepository folderRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createItemWithValidDescriptionShouldCreateItem() {
        // Arrange
        String description = "Test Item";
        FolderModel folder = new FolderModel();
        ItemModel expectedItem = new ItemModel(description);
        when(itemRepository.save(any(ItemModel.class))).thenReturn(expectedItem);

        // Act
        ItemModel result = itemService.createItem(description, folder);

        // Assert
        assertNotNull(result);
        assertEquals(description, result.getDescription());
        verify(itemRepository).save(any(ItemModel.class));
    }

    @Test
    void createItemWithEmptyDescriptionShouldThrowException() {
        // Arrange
        String emptyDescription = "";
        FolderModel folder = new FolderModel();

        // Act & Assert
        assertThrows(NombreVacioException.class, () -> itemService.createItem(emptyDescription, folder));
        verify(itemRepository, never()).save(any(ItemModel.class));
    }

    @Test
    void getItemByDescriptionShouldReturnItem() {
        // Arrange
        String description = "Test Item";
        ItemModel expectedItem = new ItemModel(description);
        when(itemRepository.getItemByDescription(description)).thenReturn(expectedItem);

        // Act
        ItemModel result = itemService.getItemByDescription(description);

        // Assert
        assertNotNull(result);
        assertEquals(description, result.getDescription());
        verify(itemRepository).getItemByDescription(description);
    }

    @Test
    void getItemByIdShouldReturnItem() {
        // Arrange
        Long itemId = 1L;
        ItemModel expectedItem = new ItemModel();
        when(itemRepository.existsById(itemId)).thenReturn(true);
        when(itemRepository.getItemById(itemId)).thenReturn(expectedItem);

        // Act
        ItemModel result = itemService.getItemById(itemId);

        // Assert
        assertNotNull(result);
        verify(itemRepository).getItemById(itemId);
    }

    @Test
    void getItemByIdWithInvalidIdShouldThrowException() {
        // Arrange
        Long invalidId = 999L;
        when(itemRepository.existsById(invalidId)).thenReturn(false);

        // Act & Assert
        assertThrows(NoExisteElItemException.class, () -> itemService.getItemById(invalidId));
        verify(itemRepository, never()).getItemById(any());
    }

    @Test
    void updateItemWithValidDataShouldUpdateItem() {
        // Arrange
        String description = "Updated Item";
        ItemModel item = new ItemModel(description);
        when(itemRepository.save(any(ItemModel.class))).thenReturn(item);

        // Act
        ItemModel result = itemService.updateItem(item);

        // Assert
        assertNotNull(result);
        assertEquals(description, result.getDescription());
        verify(itemRepository).save(item);
    }

    @Test
    void updateItemWithEmptyDescriptionShouldThrowException() {
        // Arrange
        ItemModel item = new ItemModel("");

        // Act & Assert
        assertThrows(NombreVacioException.class, () -> itemService.updateItem(item));
        verify(itemRepository, never()).save(any(ItemModel.class));
    }

    @Test
    void deleteItemShouldDeleteItem() {
        // Arrange
        ItemModel item = new ItemModel();

        // Act
        itemService.deleteItem(item);

        // Assert
        verify(itemRepository).delete(item);
    }

    @Test
    void deleteItemsByFolderNameShouldDeleteItems() {
        // Arrange
        String folderName = "Test Folder";

        // Act
        itemService.deleteItemsByFolderName(folderName);

        // Assert
        verify(itemRepository).deleteItemsByFolderName(folderName);
    }

    @Test
    void getItemsSortedShouldReturnSortedItems() {
        // Arrange
        String sortBy = "description";
        String direction = "asc";
        List<ItemModel> expectedItems = Arrays.asList(new ItemModel(), new ItemModel());
        when(itemRepository.findAll(any(Sort.class))).thenReturn(expectedItems);

        // Act
        List<ItemModel> result = itemService.getItemsSorted(sortBy, direction);

        // Assert
        assertEquals(expectedItems.size(), result.size());
        verify(itemRepository).findAll(any(Sort.class));
    }

    @Test
    void getItemsByStateShouldReturnFilteredItems() {
        // Arrange
        Long folderId = 1L;
        List<ItemStatus> states = Arrays.asList(ItemStatus.TODO, ItemStatus.DONE);
        String sortBy = "description";
        String direction = "asc";
        List<ItemModel> expectedItems = Arrays.asList(new ItemModel(), new ItemModel());
        when(itemRepository.findByFolderAndStateSorted(eq(folderId), anyList(), eq(sortBy), eq(direction)))
            .thenReturn(expectedItems);

        // Act
        List<ItemModel> result = itemService.getItemsByState(folderId, states, sortBy, direction);

        // Assert
        assertEquals(expectedItems.size(), result.size());
        verify(itemRepository).findByFolderAndStateSorted(eq(folderId), anyList(), eq(sortBy), eq(direction));
    }

    @Test
    void deleteItemByIdShouldDeleteItem() {
        // Arrange
        Long itemId = 1L;
        ItemModel item = new ItemModel();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        // Act
        itemService.deleteItemById(itemId);

        // Assert
        verify(itemRepository).delete(item);
    }

    @Test
    void removeAllShouldDeleteAllItems() {
        // Act
        itemService.removeAll();

        // Assert
        verify(itemRepository).deleteAll();
    }
} 