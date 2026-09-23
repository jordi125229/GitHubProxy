package com.homework.microservice.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GitRepository {
    @Id
    private Long id;
    @JsonAlias(value = "full_name")
    private String fullName;
    private String description;
    @JsonAlias(value = "html_url")
    private String cloneUrl;
    @JsonAlias(value = "watchers_count")
    private String stars;
    @JsonAlias(value = "created_at")
    private LocalDate createdAt;
}
