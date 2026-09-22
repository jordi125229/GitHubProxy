package com.homework.microservice;

import java.util.Optional;

public interface GitHubRepositoryProviderPort {

    GitRepositoryPojo save(GitRepositoryPojo gitRepositoryPojo);

    Optional<GitRepositoryPojo> findByFullName(String fullName);

    void delete(GitRepositoryPojo gitRepositoryPojo);
}
