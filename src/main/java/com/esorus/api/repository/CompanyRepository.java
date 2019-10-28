package com.esorus.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esorus.api.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
