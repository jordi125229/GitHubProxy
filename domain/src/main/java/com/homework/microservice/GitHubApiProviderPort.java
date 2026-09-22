package com.homework.microservice;

public interface GitHubApiProviderPort {

    GitRepositoryPojo getRepositoryByOwnerAndName(String owner, String repoName);
}
