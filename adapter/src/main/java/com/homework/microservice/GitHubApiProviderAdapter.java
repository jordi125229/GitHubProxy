package com.homework.microservice;

import com.homework.microservice.client.GitPlaceHolderClient;
import com.homework.microservice.mappers.GitRepositoryMapper;
import com.homework.microservice.entity.GitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GitHubApiProviderAdapter implements GitHubApiProviderPort {

    private final GitPlaceHolderClient gitPlaceHolderClient;

    private final GitRepositoryMapper mapper;

    @Override
    public GitRepositoryPojo getRepositoryByOwnerAndName(String owner, String repoName) {
        GitRepository repositoryByOwnerAndName = gitPlaceHolderClient.getRepositoryByOwnerAndName(owner, repoName);
        return mapper.toPojo(repositoryByOwnerAndName);
    }
}
