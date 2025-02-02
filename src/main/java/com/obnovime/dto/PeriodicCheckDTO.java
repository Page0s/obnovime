package com.obnovime.dto;

import java.time.LocalDate;

public class PeriodicCheckDTO {
    private String namePeriodic;
    private String registrationPeriodic;
    private LocalDate renewalDatePeriodic;
    private String servicePeriodic;
    private String outpostPeriodic;
    private String periodicVehicleInspection;
    private String reminderDayPeriodic;

    // Getters and Setters
    public String getNamePeriodic() { return namePeriodic; }
    public void setNamePeriodic(String namePeriodic) { this.namePeriodic = namePeriodic; }
    
    public String getRegistrationPeriodic() { return registrationPeriodic; }
    public void setRegistrationPeriodic(String registrationPeriodic) { this.registrationPeriodic = registrationPeriodic; }
    
    public LocalDate getRenewalDatePeriodic() { return renewalDatePeriodic; }
    public void setRenewalDatePeriodic(LocalDate renewalDatePeriodic) { this.renewalDatePeriodic = renewalDatePeriodic; }
    
    public String getServicePeriodic() { return servicePeriodic; }
    public void setServicePeriodic(String servicePeriodic) { this.servicePeriodic = servicePeriodic; }
    
    public String getOutpostPeriodic() { return outpostPeriodic; }
    public void setOutpostPeriodic(String outpostPeriodic) { this.outpostPeriodic = outpostPeriodic; }
    
    public String getPeriodicVehicleInspection() { return periodicVehicleInspection; }
    public void setPeriodicVehicleInspection(String periodicVehicleInspection) { this.periodicVehicleInspection = periodicVehicleInspection; }
    
    public String getReminderDayPeriodic() { return reminderDayPeriodic; }
    public void setReminderDayPeriodic(String reminderDayPeriodic) { this.reminderDayPeriodic = reminderDayPeriodic; }
}
