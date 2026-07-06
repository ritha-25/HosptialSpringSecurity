package com.hospital.HospitalManagement.repository;

import com.hospital.HospitalManagement.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    boolean existsByName(String name);

    Optional<Hospital> findByName(String name);
}
