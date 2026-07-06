package com.hospital.HospitalManagement.service;

import com.hospital.HospitalManagement.dto.DoctorCreateRequest;
import com.hospital.HospitalManagement.dto.DoctorResponse;
import com.hospital.HospitalManagement.dto.DoctorUpdateRequest;
import com.hospital.HospitalManagement.entity.Admin;
import com.hospital.HospitalManagement.entity.Doctor;
import com.hospital.HospitalManagement.entity.Hospital;
import com.hospital.HospitalManagement.entity.User;
import com.hospital.HospitalManagement.enums.Role;
import com.hospital.HospitalManagement.repository.AdminRepository;
import com.hospital.HospitalManagement.repository.DoctorRepository;
import com.hospital.HospitalManagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository doctorRepository,
                         UserRepository userRepository,
                         AdminRepository adminRepository,
                         PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public DoctorResponse createDoctor(String adminEmail, DoctorCreateRequest request) {

        User adminUser = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        Admin admin = adminRepository.findByUser(adminUser)
                .orElseThrow(() -> new RuntimeException("Admin profile not found"));
        Hospital hospital = admin.getHospital();

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("This email is already in use");
        }
        if (doctorRepository.existsByMedicalLicenseNumber(request.getMedicalLicenseNumber())) {
            throw new RuntimeException("This medical license number is already registered");
        }

        User doctorUser = new User();
        doctorUser.setEmail(request.getEmail());
        doctorUser.setPassword(passwordEncoder.encode(request.getPassword()));
        doctorUser.setRole(Role.DOCTOR);
        userRepository.save(doctorUser);

        Doctor doctor = new Doctor();
        doctor.setMedicalLicenseNumber(request.getMedicalLicenseNumber());
        doctor.setFullName(request.getFullName());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setGender(request.getGender());
        doctor.setSpecialisation(request.getSpecialisation());
        doctor.setHospital(hospital);
        doctor.setUser(doctorUser);
        doctor.setActive(true);
        doctorRepository.save(doctor);

        return toResponse(doctor);
    }

    public List<DoctorResponse> getDoctorsInMyHospital(String adminEmail) {
        User adminUser = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        Admin admin = adminRepository.findByUser(adminUser)
                .orElseThrow(() -> new RuntimeException("Admin profile not found"));

        return doctorRepository.findByHospital(admin.getHospital())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return toResponse(doctor);
    }

    @Transactional
    public DoctorResponse updateDoctor(Long id, DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (request.getFullName() != null) doctor.setFullName(request.getFullName());
        if (request.getPhoneNumber() != null) doctor.setPhoneNumber(request.getPhoneNumber());
        if (request.getGender() != null) doctor.setGender(request.getGender());
        if (request.getSpecialisation() != null) doctor.setSpecialisation(request.getSpecialisation());

        doctorRepository.save(doctor);
        return toResponse(doctor);
    }

    @Transactional
    public DoctorResponse setDoctorActiveStatus(Long id, boolean active) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setActive(active);
        doctorRepository.save(doctor);
        return toResponse(doctor);
    }

    public DoctorResponse getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return toResponse(doctor);
    }

    private DoctorResponse toResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setMedicalLicenseNumber(doctor.getMedicalLicenseNumber());
        response.setFullName(doctor.getFullName());
        response.setEmail(doctor.getUser().getEmail());
        response.setPhoneNumber(doctor.getPhoneNumber());
        response.setGender(doctor.getGender());
        response.setSpecialisation(doctor.getSpecialisation());
        response.setActive(doctor.isActive());
        response.setHospitalName(doctor.getHospital().getName());
        return response;
    }
}
