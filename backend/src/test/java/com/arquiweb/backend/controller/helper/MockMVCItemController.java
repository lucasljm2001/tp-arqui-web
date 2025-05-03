package com.arquiweb.backend.controller.helper;

import com.arquiweb.backend.controllers.dto.CreateItemDTO;
import com.arquiweb.backend.controllers.dto.ItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class MockMVCItemController {

    @Autowired
    private MockMvc mockMvcItem;

    @Autowired
    private ObjectMapper objectMapper;

    public ResultActions performRequest(MockHttpServletRequestBuilder requestBuilder) throws Throwable {
        try {
            return mockMvcItem.perform(requestBuilder);
        } catch (NestedServletException e) {
            throw (e.getCause() != null ? e.getCause() : e);
        }
    }

    public ItemDTO createItem(Long folderId, CreateItemDTO itemDTO, HttpStatus expectedStatus) throws Throwable {
        String res = performRequest(
                post("/folders/" + folderId + "/items")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemDTO))
        )
                .andExpect(status().is(expectedStatus.value()))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(res, ItemDTO.class);
    }

    public ItemDTO updateItem(Long itemId, CreateItemDTO itemDTO, HttpStatus expectedStatus) throws Throwable {
        String res = performRequest(
                patch("/items/" + itemId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemDTO))
        )
                .andExpect(status().is(expectedStatus.value()))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(res, ItemDTO.class);
    }

    public ItemDTO toggleItemStatus(Long itemId, HttpStatus expectedStatus) throws Throwable {
        String res = performRequest(
                patch("/items/" + itemId + "/toogle")
        )
                .andExpect(status().is(expectedStatus.value()))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(res, ItemDTO.class);
    }

    public void deleteItem(Long itemId, HttpStatus expectedStatus) throws Throwable {
        performRequest(
                delete("/items/" + itemId)
        )
                .andExpect(status().is(expectedStatus.value()));
    }

    public List<ItemDTO> getItems(Long folderId, String sortBy, String direction, String state1, String state2, HttpStatus expectedStatus) throws Throwable {
        String res = performRequest(
                get("/folders/" + folderId + "/items")
                        .param("sortBy", sortBy)
                        .param("direction", direction)
                        .param("state1", state1)
                        .param("state2", state2)
        )
                .andExpect(status().is(expectedStatus.value()))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(res, objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class));
    }
}