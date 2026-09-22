package com.homework.microservice.controller;

import com.homework.microservice.model.dto.GitRepositoryDto;
import com.homework.microservice.services.GitLocalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/local")
@Slf4j
public class GitLocalController {

    private final GitLocalService gitLocalService;

    @GetMapping("/repositories/{owner}/{repository-name}")
    GitRepositoryDto getRepositoryFromLocalDataBase(@PathVariable("owner") String owner, @PathVariable("repository-name") String name) {
        log.info("Received request to get local repository by owner={} and repository name ={}", owner, name);
        return gitLocalService.getRepositoryFromLocalDataBase(owner, name);
    }

    @DeleteMapping("/repositories/{owner}/{repository-name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepository(@PathVariable("owner") String owner, @PathVariable("repository-name") String name) {
        log.info("Received request to delete local repository by owner={} and repository name={}", owner, name);
        gitLocalService.delete(owner, name);
    }
}
