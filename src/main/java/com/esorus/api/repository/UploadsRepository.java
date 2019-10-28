package com.esorus.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esorus.api.domain.Uploads;

@Repository
public interface UploadsRepository extends JpaRepository<Uploads, Long>{
	Optional<Uploads> findOneBySavedFileName(String slug);
}
