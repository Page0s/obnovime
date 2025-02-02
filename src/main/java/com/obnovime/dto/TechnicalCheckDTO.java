package com.obnovime.dto;

import java.time.LocalDate;

public class TechnicalCheckDTO {
    private String vehicleTechnicalInspectionName;
    private String registrationTechnicalInspection;
    private LocalDate renewalDateTechnicalInspection;
    private String serviceTechnicalInspection;
    private String outpostTechnicalInspection;
    private String reminderDayVehicleInspection;

    // Getters and Setters
    public String getVehicleTechnicalInspectionName() { return vehicleTechnicalInspectionName; }
    public void setVehicleTechnicalInspectionName(String vehicleTechnicalInspectionName) { this.vehicleTechnicalInspectionName = vehicleTechnicalInspectionName; }
    
    public String getRegistrationTechnicalInspection() { return registrationTechnicalInspection; }
    public void setRegistrationTechnicalInspection(String registrationTechnicalInspection) { this.registrationTechnicalInspection = registrationTechnicalInspection; }
    
    public LocalDate getRenewalDateTechnicalInspection() { return renewalDateTechnicalInspection; }
    public void setRenewalDateTechnicalInspection(LocalDate renewalDateTechnicalInspection) { this.renewalDateTechnicalInspection = renewalDateTechnicalInspection; }
    
    public String getServiceTechnicalInspection() { return serviceTechnicalInspection; }
    public void setServiceTechnicalInspection(String serviceTechnicalInspection) { this.serviceTechnicalInspection = serviceTechnicalInspection; }
    
    public String getOutpostTechnicalInspection() { return outpostTechnicalInspection; }
    public void setOutpostTechnicalInspection(String outpostTechnicalInspection) { this.outpostTechnicalInspection = outpostTechnicalInspection; }
    
    public String getReminderDayVehicleInspection() { return reminderDayVehicleInspection; }
    public void setReminderDayVehicleInspection(String reminderDayVehicleInspection) { this.reminderDayVehicleInspection = reminderDayVehicleInspection; }
}
