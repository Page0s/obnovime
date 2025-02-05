package com.obnovime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {
    // Getters and Setters
    private String nameCertificate;
    private String surnnameCertificate;
    private String identificationNumberCertificate;
    private LocalDate renewalDateCertificate;
    private String outpostCertificate;
    private int reminderDayCertificate;
}
