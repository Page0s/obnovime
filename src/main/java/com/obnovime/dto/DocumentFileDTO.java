package com.obnovime.dto;

import com.obnovime.model.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DocumentFileDTO {
    private Long id;
    private String name = "-";
    private String number = "-";
    private LocalDate renewalDate = LocalDate.MIN;
    private Integer renewalPeriod = 0;
    private String serviceProvider = "-";
    private Boolean arhiva = false;
    private String documentTypeName = "-";
    private String locationName = "-";
    private String resourceTypeName = "-";
    private String statusName = "-";
    private String badgeClass = "bg-secondary";
    private String rowColor = "";
    private String responsiblePersonName = "-";

    public static DocumentFileDTO fromEntity(DocumentFile entity) {
        DocumentFileDTO dto = new DocumentFileDTO();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName() != null ? entity.getName() : "-");
        dto.setNumber(entity.getNumber() != null ? entity.getNumber() : "-");
        dto.setRenewalDate(entity.getRenewalDate() != null ? entity.getRenewalDate() : LocalDate.MIN);
        dto.setServiceProvider(entity.getServiceProvider() != null ? entity.getServiceProvider() : "-");
        dto.setArhiva(entity.getArhiva() != null ? entity.getArhiva() : false);
        
        // Handle nested objects
        if (entity.getDocumentType() != null) {
            dto.setDocumentTypeName(entity.getDocumentType().getName() != null ? 
                entity.getDocumentType().getName() : "-");
            dto.setRenewalPeriod(entity.getDocumentType().getRenewalPeriod() != null ? 
                entity.getDocumentType().getRenewalPeriod() : 0);
        }

        if (entity.getLocation() != null) {
            dto.setLocationName(entity.getLocation().getName() != null ? 
                entity.getLocation().getName() : "-");
        }
        
        if (entity.getResourceType() != null) {
            dto.setResourceTypeName(entity.getResourceType().getName() != null ? 
                entity.getResourceType().getName() : "-");
        }

        if (entity.getStatus() != null) {
            // update the status name and badge class
            String statusName = entity.getStatus().getName();
            dto.setStatusName(statusName != null ? statusName : "-");

            dto.setBadgeClass("badge-renewal-progress");
        }

        if (entity.getResponsiblePerson() != null) {
            dto.setResponsiblePersonName(entity.getResponsiblePerson().getFirstName() + " " +
                    entity.getResponsiblePerson().getLastName());
        } else {
            dto.setResponsiblePersonName("N/A");
        }
        return dto;
    }
}
