package com.esorus.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esorus.api.domain.ProjectType;

public interface ProjectTypeRepository extends JpaRepository<ProjectType,Long> {
	Optional<ProjectType> findOneBySlug(String slug);
}
