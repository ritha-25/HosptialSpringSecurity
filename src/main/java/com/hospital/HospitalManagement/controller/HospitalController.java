package com.hospital.HospitalManagement.controller;

import com.hospital.HospitalManagement.dto.HospitalRegisterRequest;
import com.hospital.HospitalManagement.dto.HospitalResponse;
import com.hospital.HospitalManagement.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping("/register")
    public ResponseEntity<HospitalResponse> register(@Valid @RequestBody HospitalRegisterRequest request) {
        HospitalResponse response = hospitalService.registerHospital(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponse> getHospital(@PathVariable Long id) {
        return ResponseEntity.ok(hospitalService.getHospital(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HospitalResponse> updateHospital(@PathVariable Long id,
                                                            @Valid @RequestBody HospitalRegisterRequest request) {
        return ResponseEntity.ok(hospitalService.updateHospital(id, request));
    }
}
