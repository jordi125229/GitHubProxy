package com.homework.microservice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class GitRepositoryPojo {
    private Long id;
    private String fullName;
    private String description;
    private String cloneUrl;
    private String stars;
    private LocalDate createdAt;
}
