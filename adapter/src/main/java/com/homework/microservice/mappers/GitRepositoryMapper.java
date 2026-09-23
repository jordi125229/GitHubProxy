package com.homework.microservice.mappers;

import com.homework.microservice.GitRepositoryPojo;
import com.homework.microservice.entity.GitRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GitRepositoryMapper {

    GitRepository toEntity(GitRepositoryPojo gitRepository);

    GitRepositoryPojo toPojo (GitRepository gitRepository);
}
