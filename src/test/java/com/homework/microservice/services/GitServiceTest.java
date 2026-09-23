package com.homework.microservice.services;
import com.homework.microservice.client.GitPlaceHolderClient;
import com.homework.microservice.model.dto.GitRepositoryDto;
import com.homework.microservice.model.entity.GitRepository;
import com.homework.microservice.model.mapper.GitRepositoryMapper;
import com.homework.microservice.repositories.RepositoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GitServiceTest {

    private GitPlaceHolderClient gitPlaceHolderClient;
    private RepositoryRepository repository;
    private GitRepositoryMapper gitRepositoryMapper;
    private GitService gitService;

    @BeforeEach
    void setUp() {
        this.gitPlaceHolderClient = Mockito.mock(GitPlaceHolderClient.class);
        this.repository = Mockito.mock(RepositoryRepository.class);
        this.gitRepositoryMapper = Mappers.getMapper(GitRepositoryMapper.class);
        this.gitService = new GitService(gitPlaceHolderClient, repository, gitRepositoryMapper);
    }

    @Test
    void getRepositoryByOwnerAndName_DataCorrect_RepositoryReturned() {
        // given
        GitRepository gitRepository = GitRepository.builder()
                .fullName("name")
                .createdAt(LocalDate.of(2026, 4, 15))
                .stars("4")
                .build();
        when(gitPlaceHolderClient.getRepositoryByOwnerAndName(anyString(), anyString())).thenReturn(gitRepository);

        // when
        GitRepository repositoryByOwnerAndName = gitPlaceHolderClient.getRepositoryByOwnerAndName("owner", "name");

        // then
        assertAll(
                () -> assertEquals("name", repositoryByOwnerAndName.getFullName()),
                () -> assertEquals("4", repositoryByOwnerAndName.getStars()),
                () -> assertEquals(LocalDate.of(2026, 4, 15), repositoryByOwnerAndName.getCreatedAt())
        );
    }

    @Test
    void saveGitRepository_DataCorrect_RepositorySaved() {
        // given
        GitRepository gitRepository = GitRepository.builder()
                .fullName("name")
                .createdAt(LocalDate.of(2026, 4, 15))
                .stars("4")
                .build();
        when(gitPlaceHolderClient.getRepositoryByOwnerAndName(anyString(), anyString())).thenReturn(gitRepository);

        // when
        GitRepositoryDto gitRepositoryDto = gitService.saveGitRepository("owner", "repoName");

        // then
        assertAll(
                () -> assertEquals("name", gitRepositoryDto.getFullName()),
                () -> assertEquals("4", gitRepositoryDto.getStars()),
                () -> assertEquals(LocalDate.of(2026, 4, 15), gitRepositoryDto.getCreatedAt())
        );
        verify(repository).save(gitRepository);
    }

    @Test
    void updateRepository_DataCorrect_RepositoryUpdated() {
        // given
        GitRepository gitRepository = GitRepository.builder()
                .fullName("fullName")
                .createdAt(LocalDate.of(2026, 4, 15))
                .stars("4")
                .build();
        when(gitPlaceHolderClient.getRepositoryByOwnerAndName(anyString(), anyString())).thenReturn(gitRepository);
        when(repository.findByFullName(anyString())).thenReturn(Optional.of(gitRepository));

        // when
        gitService.updateRepository("owner", "fullName");

        // then
        verify(repository).save(gitRepository);
    }
}
