package com.obnovime.dto;

import com.obnovime.model.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DocumentFileDTO {
    private Long id;
    private String name = "N/A";
    private String number = "N/A";
    private LocalDate renewalDate = LocalDate.MIN;
    private Integer renewalPeriod = 0;
    private String serviceProvider = "N/A";
    private Boolean arhiva = false;
    private String documentTypeName = "N/A";
    private String locationName = "N/A";
    private String resourceTypeName = "N/A";
    private String statusName = "N/A";
    private String badgeClass = "bg-secondary";
    private String rowColor = "";

    public static DocumentFileDTO fromEntity(DocumentFile entity) {
        DocumentFileDTO dto = new DocumentFileDTO();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName() != null ? entity.getName() : "N/A");
        dto.setNumber(entity.getNumber() != null ? entity.getNumber() : "N/A");
        dto.setRenewalDate(entity.getRenewalDate() != null ? entity.getRenewalDate() : LocalDate.MIN);
        dto.setRenewalPeriod(entity.getRenewalPeriod() != null ? entity.getRenewalPeriod() : 0);
        dto.setServiceProvider(entity.getServiceProvider() != null ? entity.getServiceProvider() : "N/A");
        dto.setArhiva(entity.getArhiva() != null ? entity.getArhiva() : false);
        
        // Handle nested objects
        if (entity.getDocumentType() != null) {
            dto.setDocumentTypeName(entity.getDocumentType().getName() != null ? 
                entity.getDocumentType().getName() : "N/A");
        }
        
        if (entity.getLocation() != null) {
            dto.setLocationName(entity.getLocation().getName() != null ? 
                entity.getLocation().getName() : "N/A");
        }
        
        if (entity.getResourceType() != null) {
            dto.setResourceTypeName(entity.getResourceType().getName() != null ? 
                entity.getResourceType().getName() : "N/A");
        }
        
        if (entity.getStatus() != null) {
            // update the status name and badge class
            dto.setStatusName(entity.getStatus().getName() != null ? 
                entity.getStatus().getName() : "N/A");
                
            // Set badge class based on status
            if ("Nema obnove".equals(entity.getStatus().getName()) || 
                "Aktivno".equals(entity.getStatus().getName())) {
                dto.setBadgeClass("badge-status-active");
            } else if ("Vrijeme za obnovu".equals(entity.getStatus().getName())) {
                dto.setBadgeClass("badge-renewal-progress");
            } else {
                dto.setBadgeClass("bg-secondary");
            }
        }

        // Calculate row color based on renewal date and period
        if (entity.getRenewalDate() != null && entity.getRenewalPeriod() != null) {
            LocalDate today = LocalDate.now();
            LocalDate renewalDate = entity.getRenewalDate();
            LocalDate alertDate = renewalDate.minusDays(entity.getRenewalPeriod());

            if (today.isAfter(renewalDate)) {
                dto.setRowColor("status-expired");
            } else if (!today.isBefore(alertDate)) {
                dto.setRowColor("status-renewal");
            } else {
                dto.setRowColor("status-active");
            }
        } else {
            dto.setRowColor("status-active"); // default color if dates are null
        }
        
        return dto;
    }
}
