package com.projmanager.manager.repository;

import com.projmanager.manager.models.Project;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	Optional<Project> findByProjNumber(String projNumber);
}
