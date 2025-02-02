package com.obnovime.dto;

import java.time.LocalDate;

public class CertificateDTO {
    private String nameCertificate;
    private String surnnameCertificate;
    private String identificationNumberCertificate;
    private LocalDate renewalDateCertificate;
    private String outpostCertificate;
    private int reminderDayCertificate;


    // Getters and Setters
    public String getNameCertificate() { return nameCertificate; }
    public void setNameCertificate(String nameCertificate) { this.nameCertificate = nameCertificate; }
    
    public String getSurnnameCertificate() { return surnnameCertificate; }
    public void setSurnnameCertificate(String surnnameCertificate) { this.surnnameCertificate = surnnameCertificate; }
    
    public String getIdentificationNumberCertificate() { return identificationNumberCertificate; }
    public void setIdentificationNumberCertificate(String identificationNumberCertificate) { this.identificationNumberCertificate = identificationNumberCertificate; }
    
    public LocalDate getRenewalDateCertificate() { return renewalDateCertificate; }
    public void setRenewalDateCertificate(LocalDate renewalDateCertificate) { this.renewalDateCertificate = renewalDateCertificate; }
    
    public String getOutpostCertificate() { return outpostCertificate; }
    public void setOutpostCertificate(String outpostCertificate) { this.outpostCertificate = outpostCertificate; }

    public int getReminderDayCertificate() { return reminderDayCertificate; }
    public void setReminderDayCertificate(int reminderDayCertificate) { this.reminderDayCertificate = reminderDayCertificate; }
}
