package com.obnovime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DokumentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;
    private LocalDate renewalDate;
    private Integer renewalPeriod;
    private String status;
    private String serviceProvider;
    private String location;
    private String department;
    private String resourceType;
    private String documentType;
    private Boolean arhiva;

    @Transient
    public String getRowColor() {
        LocalDate today = LocalDate.now();
        LocalDate alertDate = renewalDate.minusDays(renewalPeriod);

        if(today.isAfter(renewalDate)) {
            return "status-expired"; // Crvena
        } else if(!today.isBefore(alertDate)) {
            return "status-renewal"; // Narančasta
        } else {
            return "status-active"; // Bijela
        }
    }

    @Transient
    public String getBadgeClass() {
        if ("Nema obnove".equals(status) || "Aktivno".equals(status)) {
            return "bg-success";
        } else if ("Pokreni obnovu".equals(status)) {
            return "badge-renewal-progress";
        } else if ("Obnova u tijeku".equals(status)) {
            return "badge-renewal";
        }
        return "bg-secondary"; // default color
    }
}