package com.homework.microservice.controller;

import com.homework.microservice.model.GitRepository;
import com.homework.microservice.services.GitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "/repositories")
public class GitController {

    private final GitService gitService;

    @Operation(summary = "Getting repository from Git Hub by its owner's name and its name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Repository found."),
            @ApiResponse(responseCode = "404", description = "Not found.")})
    @GetMapping("/repositories/{owner}/{repo}")
    GitRepository getRepositoryByOwnerAndName(@PathVariable("owner") String owner, @PathVariable("repo") String name) {
        return gitService.getRepositoryByOwnerAndName(owner, name);
    }

    @PostMapping("/repositories/{owner}/{repo}")
    public GitRepository saveRepository(@PathVariable("owner") String owner, @PathVariable("repo") String name) {
        return gitService.saveGitRepository(owner, name);
    }
}
