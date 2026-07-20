package com.homework.microservice.services;

import com.homework.microservice.client.GitPlaceHolderClient;
import com.homework.microservice.model.GitRepository;
import com.homework.microservice.repositories.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitService {

    private final GitPlaceHolderClient gitPlaceHolderClient;
    private final RepositoryRepository repository;

    public GitRepository getRepositoryByOwnerAndName(String owner, String repoName) {
        return gitPlaceHolderClient.getRepositoryByOwnerAndName(owner, repoName);
    }

    public GitRepository saveGitRepository(String owner, String repoName) {
        GitRepository returnedRepository = gitPlaceHolderClient.getRepositoryByOwnerAndName(owner, repoName);
        repository.save(returnedRepository);
        return returnedRepository;
    }
}
