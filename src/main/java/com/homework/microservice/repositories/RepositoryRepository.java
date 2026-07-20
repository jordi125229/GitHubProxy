package com.homework.microservice.repositories;

import com.homework.microservice.model.GitRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryRepository extends JpaRepository<GitRepository, Long> {

}
