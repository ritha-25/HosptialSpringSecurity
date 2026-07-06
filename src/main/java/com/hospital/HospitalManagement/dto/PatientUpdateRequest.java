package com.hospital.HospitalManagement.dto;

import com.hospital.HospitalManagement.enums.Gender;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;


@Data
public class PatientUpdateRequest {

    private String fullName;

    @Past(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    private Gender gender;

    private String phoneNumber;

    private String address;

    private String emergencyContactName;

    private String emergencyContactPhone;
}
