package com.homework.microservice.controller;

import com.homework.microservice.model.dto.GitRepositoryDto;
import com.homework.microservice.services.GitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "/repositories")
@RequestMapping("/repositories")
public class GitController {

    private final GitService gitService;

    @Operation(summary = "Getting repository from Git Hub by its owner's name and its name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Repository found."),
            @ApiResponse(responseCode = "404", description = "Not found.")})
    @GetMapping("/{owner}/{repository-name}")
    GitRepositoryDto getRepositoryByOwnerAndName(@PathVariable("owner") String owner, @PathVariable("repository-name") String name) {
        log.info("Received request to get repository by owner={} and repository name={}", owner, name);
        return gitService.getRepositoryByOwnerAndName(owner, name);
    }

    @PostMapping("/{owner}/{repository-name}")
    public GitRepositoryDto saveRepository(@PathVariable("owner") String owner, @PathVariable("repository-name") String name) {
        log.info("Received request to save repository by owner={} and repository name={}", owner, name);
        return gitService.saveGitRepository(owner, name);
    }

    @PutMapping("/{owner}/{repository-name}")
    public GitRepositoryDto updateRepository(@PathVariable("owner") String owner, @PathVariable("repository-name") String name) {
        log.info("Received request to update repository by owner={} and repository name={}", owner, name);
        return gitService.updateRepository(owner, name);
    }
}
