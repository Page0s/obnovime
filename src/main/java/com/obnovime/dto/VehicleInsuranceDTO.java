package com.obnovime.dto;

import java.time.LocalDate;

public class VehicleInsuranceDTO {
    private String vehicleInsuranceName;
    private String vehicleInsuranceRegistration;
    private LocalDate renewalDateVehicleInsurance;
    private String serviceVehicleInsurance;
    private String vehicleInsuranceOutpost;
    private String reminderDayVehicleInsurance;

    // Getters and Setters
    public String getVehicleInsuranceName() { return vehicleInsuranceName; }
    public void setVehicleInsuranceName(String vehicleInsuranceName) { this.vehicleInsuranceName = vehicleInsuranceName; }
    
    public String getVehicleInsuranceRegistration() { return vehicleInsuranceRegistration; }
    public void setVehicleInsuranceRegistration(String vehicleInsuranceRegistration) { this.vehicleInsuranceRegistration = vehicleInsuranceRegistration; }
    
    public LocalDate getRenewalDateVehicleInsurance() { return renewalDateVehicleInsurance; }
    public void setRenewalDateVehicleInsurance(LocalDate renewalDateVehicleInsurance) { this.renewalDateVehicleInsurance = renewalDateVehicleInsurance; }
    
    public String getServiceVehicleInsurance() { return serviceVehicleInsurance; }
    public void setServiceVehicleInsurance(String serviceVehicleInsurance) { this.serviceVehicleInsurance = serviceVehicleInsurance; }
    
    public String getVehicleInsuranceOutpost() { return vehicleInsuranceOutpost; }
    public void setVehicleInsuranceOutpost(String vehicleInsuranceOutpost) { this.vehicleInsuranceOutpost = vehicleInsuranceOutpost; }
    
    public String getReminderDayVehicleInsurance() { return reminderDayVehicleInsurance; }
    public void setReminderDayVehicleInsurance(String reminderDayVehicleInsurance) { this.reminderDayVehicleInsurance = reminderDayVehicleInsurance; }
}
