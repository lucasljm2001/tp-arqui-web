package com.arquiweb.backend.controller.helper;

import com.arquiweb.backend.controllers.FoldersController;
import com.arquiweb.backend.controllers.dto.CreateFolderDTO;
import com.arquiweb.backend.controllers.dto.FolderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

@Component
public class MockMVCFolderController {

    @Autowired
    private MockMvc mockMvcFolder;

    @Autowired
    private FoldersController foldersController;

    @Autowired
    private ObjectMapper objectMapper;

    public ResultActions performRequest(MockHttpServletRequestBuilder requestBuilder) throws Throwable {
        try {
            return mockMvcFolder.perform(requestBuilder);
        } catch (NestedServletException e) {
            throw (e.getCause() != null ? e.getCause() : e);
        }
    }

    public List<FolderDTO> getFolders(String sortBy, String direction, HttpStatus expectedStatus) throws Throwable {
        String res = performRequest(
                get("/folders")
                        .param("sortBy", sortBy)
                        .param("direction", direction)
        )
                .andExpect(status().is(expectedStatus.value()))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(res, objectMapper.getTypeFactory().constructCollectionType(List.class, FolderDTO.class));
    }

    public FolderDTO createFolder(CreateFolderDTO folderDTO, HttpStatus expectedStatus) throws Throwable {
        String res = performRequest(
                post("/folders")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(folderDTO))
        )
                .andExpect(status().is(expectedStatus.value()))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(res, FolderDTO.class);
    }

    public FolderDTO updateFolder(Long id, CreateFolderDTO folderDTO, HttpStatus expectedStatus) throws Throwable {
        String res = performRequest(
                patch("/folders/" + id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(folderDTO))
        )
                .andExpect(status().is(expectedStatus.value()))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(res, FolderDTO.class);
    }

    public void deleteFolder(Long id, HttpStatus expectedStatus) throws Throwable {
        performRequest(
                delete("/folders/" + id)
        )
                .andExpect(status().is(expectedStatus.value()));
    }


}