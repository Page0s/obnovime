package com.obnovime.dto;

import java.time.LocalDate;

public class EmploymentContractDTO {
    private String employmentContractName;
    private String employmentContractSurname;
    private String identificationNumberEmploymentcontract;
    private LocalDate renewalDateEmploymentContract;
    private String outpostEmploymentContract;
    private String employmentContractDepartment;
    private String reminderDayEmploymentContract;

    // Getters and Setters
    public String getEmploymentContractName() { return employmentContractName; }
    public void setEmploymentContractName(String employmentContractName) { this.employmentContractName = employmentContractName; }
    
    public String getEmploymentContractSurname() { return employmentContractSurname; }
    public void setEmploymentContractSurname(String employmentContractSurname) { this.employmentContractSurname = employmentContractSurname; }
    
    public String getIdentificationNumberEmploymentcontract() { return identificationNumberEmploymentcontract; }
    public void setIdentificationNumberEmploymentcontract(String identificationNumberEmploymentcontract) { this.identificationNumberEmploymentcontract = identificationNumberEmploymentcontract; }
    
    public LocalDate getRenewalDateEmploymentContract() { return renewalDateEmploymentContract; }
    public void setRenewalDateEmploymentContract(LocalDate renewalDateEmploymentContract) { this.renewalDateEmploymentContract = renewalDateEmploymentContract; }
    
    public String getOutpostEmploymentContract() { return outpostEmploymentContract; }
    public void setOutpostEmploymentContract(String outpostEmploymentContract) { this.outpostEmploymentContract = outpostEmploymentContract; }
    
    public String getEmploymentContractDepartment() { return employmentContractDepartment; }
    public void setEmploymentContractDepartment(String employmentContractDepartment) { this.employmentContractDepartment = employmentContractDepartment; }
    
    public String getReminderDayEmploymentContract() { return reminderDayEmploymentContract; }
    public void setReminderDayEmploymentContract(String reminderDayEmploymentContract) { this.reminderDayEmploymentContract = reminderDayEmploymentContract; }
}
