package com.arquiweb.backend.controller;

import com.arquiweb.backend.controller.helper.MockMVCItemController;
import com.arquiweb.backend.controllers.dto.CreateItemDTO;
import com.arquiweb.backend.controllers.dto.ItemDTO;
import com.arquiweb.backend.utils.DataSpringService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemControllerTest {

    @Autowired
    private MockMVCItemController mockMvc;

    @Autowired
    private DataSpringService dataSpringService;

    @BeforeEach
    public void setUp() {
        dataSpringService.cleanAll();
        dataSpringService.loadData();
    }

    @Test
    public void createItem() throws Throwable {
        CreateItemDTO createItemDTO = new CreateItemDTO("Test Item");
        Assertions.assertEquals(14, mockMvc.getItems(1L, "description", "asc", "", "", HttpStatus.OK).size());
        ItemDTO item = mockMvc.createItem(1L, createItemDTO, HttpStatus.OK);

        List<ItemDTO> items = mockMvc.getItems(1L, "createdAt", "desc", "", "", HttpStatus.OK);
        Assertions.assertEquals("Test Item", items.get(0).getDescription());
        Assertions.assertEquals(15, mockMvc.getItems(1L, "description", "asc", "", "", HttpStatus.OK).size());

    }

    @Test
    public void updateItem() throws Throwable {
        CreateItemDTO updateItemDTO = new CreateItemDTO("A - Updated Item");
        mockMvc.updateItem(1L, updateItemDTO, HttpStatus.OK);

        List<ItemDTO> items = mockMvc.getItems(1L, "description", "asc", "", "", HttpStatus.OK);

        Assertions.assertEquals("A - Updated Item", items.get(0).getDescription());
        Assertions.assertEquals(1L, items.get(0).getItem_id());
    }

    @Test
    public void toggleItemStatus() throws Throwable {
         mockMvc.toggleItemStatus(1L, HttpStatus.OK);

        List<ItemDTO> items = mockMvc.getItems(1L, "description", "asc", "", "", HttpStatus.OK);

        ItemDTO item = items.get(0);
        Assertions.assertNotNull(item.getState());
        Assertions.assertEquals(1L, item.getItem_id());
    }

    @Test
    public void deleteItem() throws Throwable {
        List<ItemDTO> itemsBefore = mockMvc.getItems(1L, "description", "asc", "", "", HttpStatus.OK);
        Assertions.assertEquals(14, itemsBefore.size());

        mockMvc.deleteItem(1L, HttpStatus.OK);

        List<ItemDTO> itemsAfter = mockMvc.getItems(1L, "description", "asc", "", "", HttpStatus.OK);
        Assertions.assertEquals(13, itemsAfter.size());
    }

    @Test
    public void getItemsOrdered() throws Throwable {
        List<ItemDTO> items = mockMvc.getItems(1L, "description", "asc", "", "", HttpStatus.OK);

        Assertions.assertEquals(14, items.size());
        Assertions.assertEquals("Item 0", items.get(0).getDescription());
        Assertions.assertEquals("Item 1", items.get(1).getDescription());
    }

    @Test
    public void createItemInvalidData() throws Throwable {
        CreateItemDTO invalidItemDTO = new CreateItemDTO("");

        Assertions.assertThrows(Throwable.class, () -> {
            mockMvc.createItem(1L, invalidItemDTO, HttpStatus.BAD_REQUEST);
        });
    }

    @Test
    public void updateItemNotFound() throws Throwable {
        CreateItemDTO updateItemDTO = new CreateItemDTO("Updated Item");

        Assertions.assertThrows(Throwable.class, () -> {
            mockMvc.updateItem(999L, updateItemDTO, HttpStatus.NOT_FOUND);
        });
    }
    @AfterAll
    public void clean() {
        dataSpringService.cleanAll();
    }
}