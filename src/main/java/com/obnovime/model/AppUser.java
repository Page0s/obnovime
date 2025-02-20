package com.obnovime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String email;
    @Column(columnDefinition = "TEXT DEFAULT ''")
    private String password;
    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String userType;
    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String department;
    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String firstName;
    @Column(columnDefinition = "TEXT DEFAULT '-'")
    private String lastName;
    @Column(columnDefinition = "TEXT DEFAULT '0'")
    private String phoneNumber;
}
