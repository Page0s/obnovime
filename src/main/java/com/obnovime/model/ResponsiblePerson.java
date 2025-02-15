package com.obnovime.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "responsible_person")
public class ResponsiblePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT DEFAULT 'N/A'")
    private String position;

    @Column(columnDefinition = "TEXT DEFAULT 'N/A'")
    private String department;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "responsiblePerson")
    private List<DocumentFile> documentFiles;
}
