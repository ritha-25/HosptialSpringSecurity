package com.hospital.HospitalManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HospitalRegisterRequest {

    @NotBlank(message = "Hospital name is required")
    private String hospitalName;

    @NotBlank(message = "Hospital telephone is required")
    private String telephone;

    @NotBlank(message = "Hospital address is required")
    private String address;

    // Admin details
    @NotBlank(message = "Admin full name is required")
    private String adminFullName;

    @NotBlank(message = "Admin email is required")
    @Email(message = "Please provide a valid email")
    private String adminEmail;

    @NotBlank(message = "Admin password is required")
    private String adminPassword;

    @NotBlank(message = "Admin phone number is required")
    private String adminPhoneNumber;
}
