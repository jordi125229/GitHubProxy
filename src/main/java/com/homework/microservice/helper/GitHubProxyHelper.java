package com.homework.microservice.helper;

public final class GitHubProxyHelper {
    public static String getFullName(String owner, String repoName) {
        return owner + "/" + repoName;
    }
}
