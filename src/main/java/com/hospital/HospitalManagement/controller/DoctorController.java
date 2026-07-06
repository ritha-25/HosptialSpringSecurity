package com.hospital.HospitalManagement.controller;

import com.hospital.HospitalManagement.dto.DoctorCreateRequest;
import com.hospital.HospitalManagement.dto.DoctorResponse;
import com.hospital.HospitalManagement.dto.DoctorUpdateRequest;
import com.hospital.HospitalManagement.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> createDoctor(Principal principal,
                                                        @Valid @RequestBody DoctorCreateRequest request) {
        DoctorResponse response = doctorService.createDoctor(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DoctorResponse>> getAllDoctors(Principal principal) {
        return ResponseEntity.ok(doctorService.getDoctorsInMyHospital(principal.getName()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> updateDoctor(@PathVariable Long id,
                                                        @RequestBody DoctorUpdateRequest request) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> activateDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.setDoctorActiveStatus(id, true));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> deactivateDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.setDoctorActiveStatus(id, false));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<DoctorResponse> getMyProfile(Principal principal) {
        return ResponseEntity.ok(doctorService.getMyProfile(principal.getName()));
    }
}
