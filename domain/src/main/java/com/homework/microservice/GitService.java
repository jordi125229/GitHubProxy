package com.homework.microservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class GitService {

    private final GitHubApiProviderPort gitHubApiProviderPort;
    private final GitHubRepositoryProviderPort gitHubRepositoryProviderPort;

    public GitRepositoryPojo getRepositoryByOwnerAndName(String owner, String repoName) {
        log.info("Searching repository in git hub repository database by owner={} and repository name={}", owner, repoName);
        return gitHubApiProviderPort.getRepositoryByOwnerAndName(owner, repoName);
    }

    public GitRepositoryPojo saveGitRepository(String owner, String repoName) {
        GitRepositoryPojo returnedRepository = gitHubApiProviderPort.getRepositoryByOwnerAndName(owner, repoName);
        log.info("Save repository in local repository");
        return gitHubRepositoryProviderPort.save(returnedRepository);
    }

    public GitRepositoryPojo getRepositoryFromLocalDataBase(String owner, String repoName) {
        log.info("Searching repository in local database by owner={} and repository name={}", owner, repoName);
        return getGitRepository(owner, repoName);
    }

    public GitRepositoryPojo updateRepository(String owner, String repoName) {
        log.info("Get git hub repository from git hub repository by owner={} and repository name={}", owner, repoName);
        GitRepositoryPojo githubRepository = gitHubApiProviderPort.getRepositoryByOwnerAndName(owner, repoName);
        log.info("Get git hub repository from local repositoryby owner={} and repository name={}", owner, repoName);
        GitRepositoryPojo localRepository = findRepositoryByFullName(owner, repoName)
                .orElseThrow(() -> new GitProxyException("Repository not found in local database", 404));
        log.info("Updating git hub repository in local repository");
        localRepository.setDescription(githubRepository.getDescription());
        localRepository.setStars(githubRepository.getStars());
        return gitHubRepositoryProviderPort.save(localRepository);
    }

    public void delete(String owner, String repoName) {
        log.info("Deleting repository from local database by owner={} and repository name={}", owner, repoName);
        GitRepositoryPojo returnedRepository = getGitRepository(owner, repoName);
        gitHubRepositoryProviderPort.delete(returnedRepository);
        log.info("Repository deleted");
    }

    private Optional<GitRepositoryPojo> findRepositoryByFullName(String owner, String repoName) {
        String fullName = owner + "/" + repoName;
        return gitHubRepositoryProviderPort.findByFullName(fullName);
    }

    private GitRepositoryPojo getGitRepository(String owner, String repoName) {
        String fullName = owner + "/" + repoName;
        return gitHubRepositoryProviderPort.findByFullName(fullName).orElseThrow(() -> new GitProxyException("Repository not found", 404));
    }
}
