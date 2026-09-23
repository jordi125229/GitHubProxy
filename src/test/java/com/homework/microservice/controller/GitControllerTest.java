package com.homework.microservice.controller;

import com.homework.microservice.model.dto.GitRepositoryDto;
import com.homework.microservice.services.GitLocalService;
import com.homework.microservice.services.GitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockitoBean
    private GitLocalService gitLocalService;

    @Test
    void getRepositoryByOwnerAndName_DataCorrect_DataReceived() throws Exception {
        // given
        GitRepositoryDto gitRepositoryDto = GitRepositoryDto.builder()
                .fullName("name")
                .stars("4")
                .build();

        when(gitService.getRepositoryByOwnerAndName(anyString(), anyString())).thenReturn(gitRepositoryDto);

        // when & then
        mockMvc.perform(get("/repositories/owner/repo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("name"))
                .andExpect(jsonPath("$.stars").value("4"));
    }

    @Test
    void saveRepository_DataCorrect_RepositorySaved() throws Exception {
        // given
        GitRepositoryDto gitRepositoryDto = GitRepositoryDto.builder()
                .fullName("name")
                .stars("4")
                .build();

        when(gitService.saveGitRepository(anyString(), anyString())).thenReturn(gitRepositoryDto);

        // when & then
        mockMvc.perform(post("/repositories/owner/repository-name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("name"))
                .andExpect(jsonPath("$.stars").value("4"));
    }

    @Test
    void updateRepository_DataCorrect_RepositoryUpdated() throws Exception {
        // given
        GitRepositoryDto gitRepositoryDto = GitRepositoryDto.builder()
                .fullName("name")
                .stars("4")
                .build();

        when(gitService.updateRepository(anyString(), anyString())).thenReturn(gitRepositoryDto);

        // when & then
        mockMvc.perform(put("/repositories/owner/repository-name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("name"))
                .andExpect(jsonPath("$.stars").value("4"));
    }

    @Test
    void deleteRepository_DataCorrect_RepositoryDeleted() throws Exception {
        mockMvc.perform(delete("/local/repositories/owner/repository-name"))
                .andExpect(status().isNoContent());

        verify(gitLocalService).delete(anyString(), anyString());
    }
}
