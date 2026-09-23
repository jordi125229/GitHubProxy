package com.homework.microservice.configuration.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GitRepositoryDto {
    private String fullName;
    private String description;
    private String cloneUrl;
    private String stars;
    private LocalDate createdAt;
}
