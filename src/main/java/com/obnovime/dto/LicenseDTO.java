package com.obnovime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDTO {
    private String name;
    private String identificationNumber;
    private LocalDate renewalDate;
    private String service;
    private String outpost;
    private String department;
    private String resourceType;
    private String reminderDay;
}
