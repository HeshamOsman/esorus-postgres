package com.esorus.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esorus.api.domain.RequestForSupplier;

@Repository
public interface RequestForSupplierRepository extends JpaRepository<RequestForSupplier,Long> {

}
