package com.obnovime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentContractDTO {
    private String employmentContractName;
    private String employmentContractSurname;
    private String identificationNumberEmploymentcontract;
    private LocalDate renewalDateEmploymentContract;
    private String outpostEmploymentContract;
    private String employmentContractDepartment;
    private String reminderDayEmploymentContract;
}
