package com.esorus.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esorus.api.domain.ProfessionalRole;
import com.esorus.api.domain.ProjectPhase;

@Repository
public interface ProfessionalRoleRepository extends JpaRepository<ProfessionalRole, Long> {
	Optional<ProfessionalRole> findOneBySlug(String slug);
}
