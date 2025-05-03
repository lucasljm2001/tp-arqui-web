package com.arquiweb.backend.controller;

import com.arquiweb.backend.controllers.FoldersController;
import com.arquiweb.backend.controllers.ItemsController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Configuration
@ComponentScan(basePackages = "com.arquiweb.backend")
public class MockMVCConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    @Bean
    public MockMvc mockMvcFolder(FoldersController foldersController) {
        return MockMvcBuilders.standaloneSetup(foldersController).build();
    }

    @Bean
    public MockMvc mockMvcItem(ItemsController itemsController) {
        return MockMvcBuilders.standaloneSetup(itemsController).build();
    }
}