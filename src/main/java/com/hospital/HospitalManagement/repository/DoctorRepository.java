package com.hospital.HospitalManagement.repository;

import com.hospital.HospitalManagement.entity.Doctor;
import com.hospital.HospitalManagement.entity.Hospital;
import com.hospital.HospitalManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByMedicalLicenseNumber(String medicalLicenseNumber);

    Optional<Doctor> findByUser(User user);

    List<Doctor> findByHospital(Hospital hospital);
}
