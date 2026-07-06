package com.hospital.HospitalManagement.repository;

import com.hospital.HospitalManagement.entity.Admin;
import com.hospital.HospitalManagement.entity.Hospital;
import com.hospital.HospitalManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUser(User user);

    Optional<Admin> findByHospital(Hospital hospital);
}
