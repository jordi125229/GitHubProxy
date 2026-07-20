package com.homework.microservice.controller;

import com.homework.microservice.model.GitRepository;
import com.homework.microservice.services.GitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class GitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GitService gitService;

    @Test
    void getRepositoryByOwnerAndName_DataCorrect_DataReceived() throws Exception {
        // given
        GitRepository gitRepository = GitRepository.builder()
                .fullName("name")
                .createdAt(LocalDate.of(2026, 4, 15))
                .stars("4")
                .build();
        when(gitService.getRepositoryByOwnerAndName(anyString(), anyString())).thenReturn(gitRepository);

        // when & then
        mockMvc.perform(get("/repositories/owner/repo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("name"))
                .andExpect(jsonPath("$.stars").value("4"));
    }
}
