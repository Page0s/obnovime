package com.obnovime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalCheckDTO {
    private String vehicleTechnicalInspectionName;
    private String registrationTechnicalInspection;
    private LocalDate renewalDateTechnicalInspection;
    private String serviceTechnicalInspection;
    private String outpostTechnicalInspection;
    private String reminderDayVehicleInspection;
}
