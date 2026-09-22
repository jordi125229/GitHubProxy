package com.homework.microservice.feignFallBack;

import com.homework.microservice.client.GitPlaceHolderClient;
import com.homework.microservice.exception.GitProxyException;
import com.homework.microservice.helper.GitHubProxyHelper;
import com.homework.microservice.model.entity.GitRepository;
import com.homework.microservice.repositories.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FallBackGitPlaceHolderClient implements FallbackFactory<GitPlaceHolderClient> {

    private final RepositoryRepository repository;

    @Override
    public GitPlaceHolderClient create(Throwable cause) {
        log.error("Error");
        return new GitPlaceHolderClient() {
            @Override
            public GitRepository getRepositoryByOwnerAndName(String owner, String repo) {
                log.info("[Fallback] getRepositoryByOwnerAndName");
                String fullName = GitHubProxyHelper.getFullName(owner, repo);
                return repository.findByFullName(fullName)
                        .orElseThrow(() -> new GitProxyException("Repository not found", HttpStatus.NOT_FOUND));
            }
        };
    }
}
