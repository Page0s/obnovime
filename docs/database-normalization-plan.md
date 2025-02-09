# Database Normalization Plan for DocAlert

## Current State Analysis

### Current Model Structure
Currently, the application has a single table/entity `DocumentFile` with the following fields:
- id (Long)
- name (String)
- number (String)
- renewalDate (LocalDate)
- renewalPeriod (Integer)
- status (String)
- serviceProvider (String)
- location (String)
- resourceType (String)
- documentType (String)
- arhiva (Boolean)

### Identified Hardcoded Values
From the DocumentEntryForm.html, we've identified several sets of hardcoded values that should be normalized:

1. **Document Types**
   - Licenca
   - Periodički pregled vozila
   - Svjedodžba
   - Tehnički pregled vozila
   - Osiguranje vozila
   - Ugovor o radu

2. **Locations (Ispostave)**
   - Krapina
   - Donja Stubica
   - Zabok
   - Zlatar
   - Klanjec
   - Konjščina
   - Marija Bistrica
   - Pregrada

3. **Resource Types (Vrste resursa)**
   - Oprema
   - Radnik

4. **Document Statuses**
   - Aktivno
   - Vrijeme za obnovu
   - Nema obnove

5. **Vehicle Age Categories** (Specific to "Periodički pregled vozila")
   - Jednom godišnje za vozila mlađa od 6 godina (365 days)
   - Svakih 6 mjeseci za vozila starosti 6-10 godina (182 days)
   - Svakih 3 mjeseca za vozila starija od 10 godina (90 days)

## Proposed Database Schema

### 1. Users Table
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) NOT NULL, -- CLIENT or ADMIN
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 2. Document Types Table
```sql
CREATE TABLE document_types (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Vehicle Inspection Periods Table
```sql
CREATE TABLE vehicle_inspection_periods (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(100) NOT NULL,
    days_until_renewal INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 4. Locations Table
```sql
CREATE TABLE locations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 5. Resource Types Table
```sql
CREATE TABLE resource_types (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 6. Document Statuses Table
```sql
CREATE TABLE document_statuses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 7. Modified DocumentFile Table
```sql
CREATE TABLE document_files (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    number VARCHAR(100), -- Used for OIB in case of Svjedodžba
    renewal_date DATE,
    renewal_period INTEGER,
    service_provider VARCHAR(255),
    document_type_id BIGINT,
    location_id BIGINT,
    resource_type_id BIGINT,
    status_id BIGINT,
    vehicle_inspection_period_id BIGINT NULL, -- Only for Periodički pregled vozila
    created_by BIGINT, -- References users.id
    arhiva BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (document_type_id) REFERENCES document_types(id),
    FOREIGN KEY (location_id) REFERENCES locations(id),
    FOREIGN KEY (resource_type_id) REFERENCES resource_types(id),
    FOREIGN KEY (status_id) REFERENCES document_statuses(id),
    FOREIGN KEY (vehicle_inspection_period_id) REFERENCES vehicle_inspection_periods(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);
```

## Initial Data Population

### 1. Korisnici (Users)
```sql
-- Unos korisnika
INSERT INTO users (email, password, user_type) VALUES
('glavna.sestra@gmail.com', 'gs123', 'CLIENT'),
('kadrovska@gmail.com', 'ka123', 'CLIENT'),
('vozni.park@gmail.com', 'vp123', 'CLIENT');
```

### 2. Lokacije (Locations)
```sql
-- Unos lokacija
INSERT INTO locations (name) VALUES
('Krapina'), ('Donja Stubica'), ('Zabok'), ('Zlatar'),
('Klanjec'), ('Konjščina'), ('Marija Bistrica'), ('Pregrada');
```

### 3. Periodi pregleda vozila (Vehicle Inspection Periods)
```sql
-- Unos perioda pregleda vozila
INSERT INTO vehicle_inspection_periods (description, days_until_renewal) VALUES
('Jednom godišnje za vozila mlađa od 6 godina', 365),
('Svakih 6 mjeseci za vozila starosti 6-10 godina', 182),
('Svakih 3 mjeseca za vozila starija od 10 godina', 90);
```

### 4. Statusi dokumenata (Document Statuses)
```sql
-- Unos statusa dokumenata
INSERT INTO document_statuses (name) VALUES
('Aktivno'), ('Vrijeme za obnovu'), ('Nema obnove'), ('Obnova u tijeku');
```

### 5. Vrste resursa (Resource Types)
```sql
-- Unos vrsta resursa
INSERT INTO resource_types (name) VALUES
('Oprema'), ('Radnik'), ('Vozilo');
```

### 6. Vrste dokumenata (Document Types)
```sql
-- Unos vrsta dokumenata
INSERT INTO document_types (name) VALUES
('Licenca'),
('Periodički pregled vozila'),
('Svjedodžba'),
('Tehnički pregled vozila'),
('Osiguranje vozila'),
('Ugovor o radu');
```

### 7. Inicijalni dokumenti (Initial Documents)
```sql
-- Unos podataka u tablicu document_files
INSERT INTO document_files (id, name, number, service_provider, location_id, resource_type_id, renewal_date, document_type_id, status_id) VALUES
(1, 'Benehaert R3 EKG aparat - Mindray', 'FK-26032761', 'Unicomp d.o.o.', NULL, (SELECT id FROM resource_types WHERE name = 'Oprema'), '2024-04-22', (SELECT id FROM document_types WHERE name = 'Licenca'), (SELECT id FROM document_statuses WHERE name = 'Vrijeme za obnovu')),
(2, 'Mortara, ELI 230 EKG aparat - Mortara Instrument', '113080154080', 'Unicomp d.o.o.', NULL, (SELECT id FROM resource_types WHERE name = 'Oprema'), '2024-04-22', (SELECT id FROM document_types WHERE name = 'Licenca'), (SELECT id FROM document_statuses WHERE name = 'Vrijeme za obnovu')),
(3, 'Aspirator na električni pogon', '40174', 'Inel-medicinska tehnika d.o.o.', NULL, (SELECT id FROM resource_types WHERE name = 'Oprema'), '2024-10-16', (SELECT id FROM document_types WHERE name = 'Licenca'), (SELECT id FROM document_statuses WHERE name = 'Vrijeme za obnovu')),
(4, 'Lifepak 15 Defibrilator - Fiziokontrol', '47416358', 'Elektroničar d.o.o.', NULL, (SELECT id FROM resource_types WHERE name = 'Oprema'), '2024-11-05', (SELECT id FROM document_types WHERE name = 'Licenca'), (SELECT id FROM document_statuses WHERE name = 'Vrijeme za obnovu')),
(5, 'Gučin K.', NULL, NULL, (SELECT id FROM locations WHERE name = 'Zabok'), (SELECT id FROM resource_types WHERE name = 'Radnik'), '2025-03-01', (SELECT id FROM document_types WHERE name = 'Svjedodžba'), (SELECT id FROM document_statuses WHERE name = 'Vrijeme za obnovu')),
(6, 'Celjak M.', NULL, NULL, (SELECT id FROM locations WHERE name = 'Zabok'), (SELECT id FROM resource_types WHERE name = 'Radnik'), '2025-03-01', (SELECT id FROM document_types WHERE name = 'Svjedodžba'), (SELECT id FROM document_statuses WHERE name = 'Vrijeme za obnovu')),
(7, 'Turk N', NULL, NULL, (SELECT id FROM locations WHERE name = 'Zabok'), (SELECT id FROM resource_types WHERE name = 'Radnik'), '2025-03-01', (SELECT id FROM document_types WHERE name = 'Svjedodžba'), (SELECT id FROM document_statuses WHERE name = 'Vrijeme za obnovu')),
(8, 'Tehnički pregled vozila', 'KR 686 - IO', 'AutoTech d.o.o.', (SELECT id FROM locations WHERE name = 'Krapina'), (SELECT id FROM resource_types WHERE name = 'Vozilo'), '2025-10-11', (SELECT id FROM document_types WHERE name = 'Tehnički pregled vozila'), (SELECT id FROM document_statuses WHERE name = 'Aktivno')),
(9, 'Tehnički pregled vozila', 'KR 687 - IO', 'AutoTech d.o.o.', (SELECT id FROM locations WHERE name = 'Krapina'), (SELECT id FROM resource_types WHERE name = 'Vozilo'), '2025-10-11', (SELECT id FROM document_types WHERE name = 'Tehnički pregled vozila'), (SELECT id FROM document_statuses WHERE name = 'Aktivno'));
```

## Implementation Steps

### Phase 1: Create New Entities
1. Create JPA entities for each new table
2. Create corresponding repositories
3. Create DTOs if needed
4. Implement basic authentication for CLIENT users

### Phase 2: Update Application Logic
1. Modify DocumentFile entity to include foreign keys
2. Update FormController to handle the new relationships
3. Create REST endpoints for fetching reference data
4. Add special handling for "Periodički pregled vozila" to automatically set renewal period based on vehicle age
5. Ensure "Svjedodžba" OIB is saved in the number field
6. Implement status transition rules (prevent "Aktivno" to "Nema obnove" transitions)

### Phase 3: Update UI
1. Modify DocumentEntryForm.html to load dropdown options from the database
2. Add JavaScript to handle dynamic loading of options
3. Update form submission logic to handle IDs instead of strings
4. Update DocumentMainForm.html to display the related data correctly

## Required Code Changes

### 1. Example Entity Class for Vehicle Inspection Periods
```java
@Entity
@Table(name = "vehicle_inspection_periods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInspectionPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String description;
    
    @Column(name = "days_until_renewal", nullable = false)
    private Integer daysUntilRenewal;
}
```

### 2. Example Status Transition Check
```java
@Service
public class DocumentService {
    public void validateStatusTransition(String currentStatus, String newStatus) {
        if ("Aktivno".equals(currentStatus) && "Nema obnove".equals(newStatus)) {
            throw new IllegalStateException("Cannot transition from Aktivno to Nema obnove");
        }
        if ("Nema obnove".equals(currentStatus) && "Aktivno".equals(newStatus)) {
            throw new IllegalStateException("Cannot transition from Nema obnove to Aktivno");
        }
    }
}
```

### 3. Example Controller Method for Loading Locations
```java
@Controller
public class FormController {
    @GetMapping("/api/locations")
    @ResponseBody
    public List<LocationDTO> getLocations() {
        return locationService.getAllActiveLocations();
    }
}
```

## Next Steps
1. Review and approve the updated schema changes
2. Begin implementation with user authentication
3. Implement the core database changes
4. Update the UI to use the new dynamic data
5. Add validation for status transitions

---
*Document updated: February 5, 2025*
