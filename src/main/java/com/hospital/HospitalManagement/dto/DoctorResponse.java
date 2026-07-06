package com.hospital.HospitalManagement.dto;

import com.hospital.HospitalManagement.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private Long id;
    private String medicalLicenseNumber;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String specialisation;
    private boolean active;
    private String hospitalName;
}
