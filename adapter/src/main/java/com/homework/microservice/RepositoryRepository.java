package com.homework.microservice;

import com.homework.microservice.entity.GitRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryRepository extends JpaRepository<GitRepository, Long> {

    Optional<GitRepository> findByFullName(String fullName);
}
