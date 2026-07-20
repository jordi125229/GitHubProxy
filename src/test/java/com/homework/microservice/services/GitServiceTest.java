package com.homework.microservice.services;

import com.homework.microservice.client.GitPlaceHolderClient;
import com.homework.microservice.model.GitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GitServiceTest {

    private GitPlaceHolderClient gitPlaceHolderClient;

    @Test
    void getRepositoryByOwnerAndName_DataCorrect_RepositoryReturned() {
        // given
        this.gitPlaceHolderClient = Mockito.mock(GitPlaceHolderClient.class);
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
}
