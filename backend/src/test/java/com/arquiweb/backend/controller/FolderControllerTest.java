package com.arquiweb.backend.controller;

import com.arquiweb.backend.controller.helper.MockMVCFolderController;
import com.arquiweb.backend.controllers.dto.CreateFolderDTO;
import com.arquiweb.backend.controllers.dto.FolderDTO;
import com.arquiweb.backend.models.FolderModel;
import com.arquiweb.backend.services.FolderService;
import com.arquiweb.backend.services.ItemService;
import com.arquiweb.backend.utils.DataSpringService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FolderControllerTest {

    @Autowired
    private MockMVCFolderController mockMvc;

    private FolderService folderService;

    @Autowired
    private DataSpringService dataSpringService;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        dataSpringService.cleanAll();
        dataSpringService.loadData();
        folderService = Mockito.mock(FolderService.class);
    }

    @Test
    public void createFolder() throws Throwable {
        CreateFolderDTO createFolderDTO = new CreateFolderDTO("Test Folder");
        FolderModel folderModel = new FolderModel();
        folderModel.setFolder_id(11L);
        folderModel.setName("Test Folder");


        FolderDTO folder = mockMvc.createFolder(createFolderDTO, HttpStatus.OK);

        Assertions.assertEquals("Test Folder", folder.getName());
        Assertions.assertEquals(11L, folder.getFolder_id());
    }

    @Test
    public void updateFolder() throws Throwable {
        CreateFolderDTO updateFolderDTO = new CreateFolderDTO("Updated Folder");

        FolderDTO folder = mockMvc.updateFolder(1L, updateFolderDTO, HttpStatus.OK);

        Assertions.assertEquals("Updated Folder",  folder.getName());
        Assertions.assertEquals(1L, folder.getFolder_id());
    }

    @Test
    public void getFoldersOrdered() throws Throwable {

        List<FolderDTO> folders = mockMvc.getFolders("name", "asc", HttpStatus.OK);

        Assertions.assertEquals(10, folders.size());
        Assertions.assertEquals("Folder 0", folders.get(0).getName());
        Assertions.assertEquals("Folder 1", folders.get(1).getName());
    }

    @Test
    public void deleteFolder() throws Throwable {
        Assertions.assertEquals(10, mockMvc.getFolders("name", "asc",HttpStatus.OK).size());


        mockMvc.deleteFolder(1L, HttpStatus.OK);

        Assertions.assertEquals(9, mockMvc.getFolders("name", "asc",HttpStatus.OK).size());
    }

    @Test
    public void deleteFolderNotFound() throws Throwable {
        Assertions.assertEquals(10, mockMvc.getFolders("name", "asc",HttpStatus.OK).size());

        Assertions.assertThrows(Throwable.class, () -> {
            mockMvc.deleteFolder(100L, HttpStatus.NOT_FOUND);
        });

    }

    @Test
    public void createFolderInvalidData() throws Throwable {
        CreateFolderDTO invalidFolderDTO = new CreateFolderDTO("");

        Assertions.assertThrows(Throwable.class, () -> {
            mockMvc.createFolder(invalidFolderDTO, HttpStatus.BAD_REQUEST);
        });
    }

    @Test
    public void updateFolderNotFound() throws Throwable {
        CreateFolderDTO updateFolderDTO = new CreateFolderDTO("Updated Folder");


        Assertions.assertThrows(Throwable.class, () -> {
            mockMvc.updateFolder(1L, updateFolderDTO, HttpStatus.NOT_FOUND);
        });

    }

    @AfterAll
    public void clean() {
        dataSpringService.cleanAll();
    }
}