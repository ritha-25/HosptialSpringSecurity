package com.hospital.HospitalManagement.dto;

import com.hospital.HospitalManagement.enums.Gender;
import lombok.Data;


@Data
public class DoctorUpdateRequest {
    private String fullName;
    private String phoneNumber;
    private Gender gender;
    private String specialisation;
}
