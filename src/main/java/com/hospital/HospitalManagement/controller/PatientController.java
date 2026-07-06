package com.hospital.HospitalManagement.controller;

import com.hospital.HospitalManagement.dto.PatientRegisterRequest;
import com.hospital.HospitalManagement.dto.PatientResponse;
import com.hospital.HospitalManagement.dto.PatientUpdateRequest;
import com.hospital.HospitalManagement.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register")
    public ResponseEntity<PatientResponse> register(@Valid @RequestBody PatientRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.register(request));
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponse> getMyProfile(Principal principal) {
        return ResponseEntity.ok(patientService.getMyProfile(principal.getName()));
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponse> updateMyProfile(Principal principal,
                                                            @Valid @RequestBody PatientUpdateRequest request) {
        return ResponseEntity.ok(patientService.updateMyProfile(principal.getName(), request));
    }
    @PatchMapping("/user")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponse> patchMyProfile(Principal principal,
                                                           @RequestBody PatientUpdateRequest request) {
        return ResponseEntity.ok(patientService.updateMyProfile(principal.getName(), request));
    }
    @DeleteMapping("/user")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<String> deleteMyAccount(Principal principal) {
        patientService.deleteMyAccount(principal.getName());
        return ResponseEntity.ok("Account deleted successfully");
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
}
