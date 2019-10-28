package com.esorus.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esorus.api.domain.Configs;


@Repository
public interface ConfigsRepository extends JpaRepository<Configs, Long>{
	Optional<Configs> findOneByKey(String key);
}
