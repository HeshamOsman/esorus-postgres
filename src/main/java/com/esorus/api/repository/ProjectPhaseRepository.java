package com.esorus.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esorus.api.domain.ProjectPhase;

public interface ProjectPhaseRepository extends JpaRepository<ProjectPhase,Long> {
	Optional<ProjectPhase> findOneBySlug(String slug);
}
