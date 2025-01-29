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
import java.util.ArrayList;
import java.util.List;

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

    @Transient
    public List<StatusOption> getStatusOptions() {
        List<StatusOption> options = new ArrayList<>();
        String color = getRowColor();

        // Dokumenti u arhivi
        if("Arhiva".equals(status)) {
            return options; // Nema opcija
        }

        // Dokumenti koji se ne obnavljaju
        if("Nema obnove".equals(status)) {
            options.add(new StatusOption("Arhiva", "Arhiva"));
            return options;
        }

        // Pravila za aktivne dokumente
        if(color.equals("status-active")) {
            options.add(new StatusOption("Arhiva", "Arhiva"));
        } 
        // Dokumenti u alarmnom periodu
        else if(color.equals("status-renewal") || color.equals("status-expired")) {
            options.add(new StatusOption("Arhiva", "Arhiva"));
            
            if(!"Obnova u tijeku".equals(status)) {
                options.add(new StatusOption("Obnova u tijeku", "Obnova u tijeku"));
            }
        }

        // Automatski postavi status za dokumente u alarmnom periodu
        if((color.equals("status-renewal") || color.equals("status-expired")) 
            && "Aktivno".equals(status)) {
            this.status = "Pokreni obnovu";
        }

        return options;
    }
}