package com.homework.microservice.client;

import com.homework.microservice.config.FeignConfiguration;
import com.homework.microservice.feignFallBack.FallBackGitPlaceHolderClient;
import com.homework.microservice.model.entity.GitRepository;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "gitRepositoryPlaceholder", configuration = FeignConfiguration.class,
        fallbackFactory = FallBackGitPlaceHolderClient.class)
public interface GitPlaceHolderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{owner}/{repo}", produces = "application/json")
    GitRepository getRepositoryByOwnerAndName(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
