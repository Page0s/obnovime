package com.obnovime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInsuranceDTO {
    private String vehicleInsuranceName;
    private String vehicleInsuranceRegistration;
    private LocalDate renewalDateVehicleInsurance;
    private String serviceVehicleInsurance;
    private String vehicleInsuranceOutpost;
    private String reminderDayVehicleInsurance;
}
