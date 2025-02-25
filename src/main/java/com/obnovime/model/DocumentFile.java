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

    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String name;
    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String number;
    private LocalDate renewalDate;
    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String serviceProvider;
    private Boolean arhiva;
    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String notes;

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
    @JoinColumn(name = "responsible_person_id")
    private AppUser responsiblePerson;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private DocumentStatus status;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private AppUser createdBy;
}