package com.obnovime.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "renewal_history")
@NoArgsConstructor
@AllArgsConstructor
public class RenewalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "past_renewal_date")
    private LocalDate pastRenewalDate = LocalDate.of(1970, 1, 1); // Using Unix epoch start date as minimum

    @ManyToOne
    @JoinColumn(name = "renewed_by_id")
    private AppUser renewedBy;

    @ManyToOne
    @JoinColumn(name = "document_file_id")
    private DocumentFile documentFile;
}
