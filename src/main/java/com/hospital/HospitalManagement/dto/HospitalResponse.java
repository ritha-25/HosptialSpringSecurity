package com.hospital.HospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalResponse {
    private Long id;
    private String name;
    private String telephone;
    private String address;
}
