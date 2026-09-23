package com.homework.microservice;
import com.homework.microservice.entity.GitRepository;
import com.homework.microservice.mappers.GitRepositoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GitHubRepositoryProviderAdapter implements GitHubRepositoryProviderPort {

    private final RepositoryRepository repository;
    private final GitRepositoryMapper mapper;

    @Override
    public GitRepositoryPojo save(GitRepositoryPojo gitRepositoryPojo) {
        GitRepository entity = mapper.toEntity(gitRepositoryPojo);
        repository.save(entity);
        return mapper.toPojo(entity);
    }

    @Override
    public Optional<GitRepositoryPojo> findByFullName(String fullName) {
        return repository.findByFullName(fullName)
                .map(mapper::toPojo);
    }

    @Override
    public void delete(GitRepositoryPojo gitRepositoryPojo) {
        GitRepository entity = mapper.toEntity(gitRepositoryPojo);
        repository.delete(entity);
    }
}
