package com.esorus.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esorus.api.domain.TypeOfWorkNeeded;

public interface TypeOfWorkNeededRepository extends JpaRepository<TypeOfWorkNeeded,Long> {
	Optional<TypeOfWorkNeeded> findOneBySlug(String slug);
}
