package com.homework.microservice.client;

import com.homework.microservice.exception.GitProxyException;
import com.homework.microservice.model.entity.GitRepository;
import com.homework.microservice.repositories.RepositoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.wiremock.spring.EnableWireMock;
import java.util.Optional;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWireMock
@AutoConfigureMockMvc
public class FeignClientTest {

    @Autowired
    private GitPlaceHolderClient client;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepositoryRepository repository;

    @Test
    void feignRetry_WhenExceptionThrown_Retry() {
        // given
        stubFor(get("/repos/owner/repoName").willReturn(aResponse().withStatus(503)));

        // when
        GitProxyException exception = assertThrows(GitProxyException.class, () -> client.getRepositoryByOwnerAndName("owner", "repoName"));

        // then
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, exception.getStatus()),
                () -> verify(5, getRequestedFor(urlEqualTo("/repos/owner/repoName")))
        );
    }

    @Test
    void getRepository_DataCorrect_RepositoryReturned() throws Exception {
        // given
        stubFor(get("/repos/owner/repository-name").willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBodyFile("github_response.json")));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/owner/repository-name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("octocat/Hello-World"))
                .andExpect(jsonPath("$.description").value("This your first repo!"));

        verify(1, getRequestedFor(urlEqualTo("/repos/owner/repository-name")));
    }

    @Test
    void saveRepository_DataCorrect_RepositoryReturned() throws Exception {
        // given
        GitRepository gitRepository = GitRepository.builder()
                .fullName("octocat/Hello-World")
                .build();
        stubFor(get("/repos/owner/repository-name").willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBodyFile("github_response.json")));

        when(repository.findByFullName(anyString())).thenReturn(Optional.of(gitRepository));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/owner/repository-name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("octocat/Hello-World"))
                .andExpect(jsonPath("$.description").value("This your first repo!"));

        verify(1, getRequestedFor(urlEqualTo("/repos/owner/repository-name")));
        assertAll(
                () -> assertEquals("octocat/Hello-World", gitRepository.getFullName())
        );
    }

    @Test
    void updateRepository_DataCorrect_RepositoryReturned() throws Exception {
        // given
        GitRepository gitRepository = GitRepository.builder()
                .id(1L)
                .fullName("owner/repository-name")
                .description("description")
                .build();

        when(repository.findByFullName(anyString())).thenReturn(Optional.of(gitRepository));
        when(repository.save(ArgumentMatchers.any(GitRepository.class))).thenReturn(gitRepository);

        stubFor(get("/repos/owner/repository-name").willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBodyFile("github_response.json")));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/owner/repository-name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("owner/repository-name"))
                .andExpect(jsonPath("$.description").value("This your first repo!"));

        GitRepository updatedRepository = repository.findByFullName("owner/repository-name").orElseThrow();

        verify(1, getRequestedFor(urlEqualTo("/repos/owner/repository-name")));
        assertAll(
                () -> assertEquals("This your first repo!", updatedRepository.getDescription())
        );
    }

    @Test
    void feignFallBack_ExceptionThrown_FallBackTriggered() {
        // given
        GitRepository gitRepository = GitRepository.builder()
                .fullName("octocat/Hello-World")
                .build();
        stubFor(get(urlEqualTo("/repos/octocat/Hello-World")).willReturn(aResponse().withStatus(500)));
        when(repository.findByFullName(anyString())).thenReturn(Optional.of(gitRepository));

        // when
        GitRepository foundFromGitRepository = client.getRepositoryByOwnerAndName("octocat", "Hello-World");

        // then
        assertAll(
                () -> assertEquals("octocat/Hello-World", foundFromGitRepository.getFullName())
        );
    }
}
