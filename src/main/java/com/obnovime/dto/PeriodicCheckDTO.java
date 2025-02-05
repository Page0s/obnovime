package com.obnovime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodicCheckDTO {
    private String namePeriodic;
    private String registrationPeriodic;
    private LocalDate renewalDatePeriodic;
    private String servicePeriodic;
    private String outpostPeriodic;
    private String periodicVehicleInspection;
    private String reminderDayPeriodic;
}
