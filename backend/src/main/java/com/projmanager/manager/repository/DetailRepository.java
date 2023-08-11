package com.projmanager.manager.repository;

import com.projmanager.manager.models.Detail;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {
	List<Detail> findByCompNumber(String compNumber);
    Optional<Detail> findByDetNumber(String detNumber);
}
