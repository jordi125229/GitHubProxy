package com.homework.microservice.model.mapper;

import com.homework.microservice.model.dto.GitRepositoryDto;
import com.homework.microservice.model.entity.GitRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GitRepositoryMapper {

    GitRepositoryDto gitRepositoryToDto(GitRepository gitRepository);
}
