package com.obnovime.model;

import jakarta.persistence.*;
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
@Table(name = "document_files")
public class DocumentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;
    private LocalDate renewalDate;
    private Integer renewalPeriod;
    private String serviceProvider;
    private Boolean arhiva;

    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "resource_type_id")
    private ResourceType resourceType;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private DocumentStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_inspection_period_id")
    private VehicleInspectionPeriod vehicleInspectionPeriod;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private AppUser createdBy;

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
        if (status != null) {
            if ("Nema obnove".equals(status.getName()) || "Aktivno".equals(status.getName())) {
                return "badge-status-active";
            } else if ("Vrijeme za obnovu".equals(status.getName())) {
                return "badge-renewal-progress";
            }
        }
        return "bg-secondary"; // default color
    }
}