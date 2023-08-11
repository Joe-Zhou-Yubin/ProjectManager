package com.projmanager.manager.repository;
import com.projmanager.manager.models.Component;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findByProjNumber(String projNumber);
    Optional<Component> findByCompNumber(String compNumber);

    @Transactional
    void deleteByCompNumber(String compNumber);
}

