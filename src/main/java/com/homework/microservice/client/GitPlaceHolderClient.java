package com.homework.microservice.client;

import com.homework.microservice.model.GitRepository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "gitRepositoryPlaceholder", url = "https://api.github.com/")
public interface GitPlaceHolderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{owner}/{repo}", produces = "application/json")
    GitRepository getRepositoryByOwnerAndName(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
