package com.obnovime.dto;

import java.time.LocalDate;

public class LicenseDTO {
    private String name;
    private String identificationNumber;
    private LocalDate renewalDate;
    private String service;
    private String outpost;
    private String department;
    private String resourceType;
    private String reminderDay;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getIdentificationNumber() { return identificationNumber; }
    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }
    
    public LocalDate getRenewalDate() { return renewalDate; }
    public void setRenewalDate(LocalDate renewalDate) { this.renewalDate = renewalDate; }
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public String getOutpost() { return outpost; }
    public void setOutpost(String outpost) { this.outpost = outpost; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }
    
    public String getReminderDay() { return reminderDay; }
    public void setReminderDay(String reminderDay) { this.reminderDay = reminderDay; }
}
