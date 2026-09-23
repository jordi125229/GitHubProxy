package com.homework.microservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    GitService gitService(GitHubApiProviderPort gitHubApiProviderPort,
                          GitHubRepositoryProviderPort gitHubRepositoryProviderPort) {
        return new GitService(gitHubApiProviderPort, gitHubRepositoryProviderPort);
    }
}
