package com.hospital.HospitalManagement.service;

import com.hospital.HospitalManagement.dto.HospitalRegisterRequest;
import com.hospital.HospitalManagement.dto.HospitalResponse;
import com.hospital.HospitalManagement.entity.Admin;
import com.hospital.HospitalManagement.entity.Hospital;
import com.hospital.HospitalManagement.entity.User;
import com.hospital.HospitalManagement.enums.Role;
import com.hospital.HospitalManagement.repository.AdminRepository;
import com.hospital.HospitalManagement.repository.HospitalRepository;
import com.hospital.HospitalManagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public HospitalService(HospitalRepository hospitalRepository,
                           UserRepository userRepository,
                           AdminRepository adminRepository,
                           PasswordEncoder passwordEncoder) {
        this.hospitalRepository = hospitalRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public HospitalResponse registerHospital(HospitalRegisterRequest request) {

        if (hospitalRepository.existsByName(request.getHospitalName())) {
            throw new RuntimeException("A hospital with this name already exists");
        }

        if (userRepository.existsByEmail(request.getAdminEmail())) {
            throw new RuntimeException("This email is already in use");
        }

        Hospital hospital = new Hospital();
        hospital.setName(request.getHospitalName());
        hospital.setTelephone(request.getTelephone());
        hospital.setAddress(request.getAddress());
        hospitalRepository.save(hospital);

        User adminUser = new User();
        adminUser.setEmail(request.getAdminEmail());
        adminUser.setPassword(passwordEncoder.encode(request.getAdminPassword()));
        adminUser.setRole(Role.ADMIN);
        userRepository.save(adminUser);

        Admin admin = new Admin();
        admin.setFullName(request.getAdminFullName());
        admin.setPhoneNumber(request.getAdminPhoneNumber());
        admin.setHospital(hospital);
        admin.setUser(adminUser);
        adminRepository.save(admin);

        return new HospitalResponse(hospital.getId(), hospital.getName(),
                hospital.getTelephone(), hospital.getAddress());
    }

    public HospitalResponse getHospital(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));
        return new HospitalResponse(hospital.getId(), hospital.getName(),
                hospital.getTelephone(), hospital.getAddress());
    }

    @Transactional
    public HospitalResponse updateHospital(Long id, HospitalRegisterRequest request) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        if (!hospital.getName().equals(request.getHospitalName())
                && hospitalRepository.existsByName(request.getHospitalName())) {
            throw new RuntimeException("A hospital with this name already exists");
        }

        hospital.setName(request.getHospitalName());
        hospital.setTelephone(request.getTelephone());
        hospital.setAddress(request.getAddress());
        hospitalRepository.save(hospital);

        return new HospitalResponse(hospital.getId(), hospital.getName(),
                hospital.getTelephone(), hospital.getAddress());
    }
}
