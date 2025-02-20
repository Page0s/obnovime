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
    private String serviceProvider = "-";
    private Boolean arhiva = false;
    private String documentTypeName = "-";
    private String locationName = "-";
    private String resourceTypeName = "-";
    private String statusName = "-";
    private String badgeClass = "bg-secondary";
    private String rowColor = "";

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

            // Set badge class based on status
//            if ("Nema obnove".equals(entity.getStatus().getName()) ||
//                "Aktivno".equals(entity.getStatus().getName())) {
//                dto.setBadgeClass("badge-status-active");
//            } else if ("Vrijeme za obnovu".equals(entity.getStatus().getName())) {
//                dto.setBadgeClass("badge-renewal-progress");
//            } else if ("Obnova u tijeku".equals(entity.getStatus().getName())) {
//                dto.setBadgeClass("badge-renewal-progress");
//            }
//            else {
//                dto.setBadgeClass("badge-renewal-progress");
//            }
//            DocumentStatus documentStatus = DocumentStatus.fromDisplayName(statusName);
//            dto.setBadgeClass(documentStatus.getBadgeClass());
        }

        // Calculate row color based on renewal date and period
        if (entity.getRenewalDate() != null) {
            LocalDate today = LocalDate.now();
            LocalDate renewalDate = entity.getRenewalDate();
//            LocalDate alertDate = renewalDate.minusDays(entity.getRenewalPeriod());

//            if (today.isAfter(renewalDate)) {
//                if (dto.getStatusName().equals("Vrijeme za obnovu")) {
//                    dto.setStatusName("Vrijeme za obnovu isteklo");
//                } else if (dto.getStatusName().equals("Obnova u tijeku")) {
//                    dto.setStatusName("Obnova u tijeku isteklo");
//                }
//            } else if (!today.isBefore(alertDate) && !dto.getStatusName().equals("Obnova u tijeku isteklo") && !dto.getStatusName().equals("Vrijeme za obnovu isteklo")) {
//                dto.setStatusName("Vrijeme za obnovu");
//            }

        }
        
        return dto;
    }
}
