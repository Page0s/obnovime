-- Unos korisnika
INSERT INTO app_user (email, password, user_type) VALUES
('glavna.sestra@gmail.com', 'gs123', 'CLIENT'),
('kadrovska@gmail.com', 'ka123', 'CLIENT'),
('vozni.park@gmail.com', 'vp123', 'CLIENT');

-- Unos lokacija
INSERT INTO locations (name) VALUES
('N/A'), ('Krapina'), ('Donja Stubica'), ('Zabok'), ('Zlatar'),
('Klanjec'), ('Konjščina'), ('Marija Bistrica'), ('Pregrada');

-- Unos vrsta resursa
INSERT INTO resource_types (name) VALUES
('Oprema'), ('Radnik'), ('Vozilo');

-- Unos statusa dokumenata
INSERT INTO document_statuses (name) VALUES
('Aktivno'), ('Vrijeme za obnovu'), ('Nema obnove'), ('Obnova u tijeku');

-- Unos perioda pregleda vozila
INSERT INTO vehicle_inspection_periods (description, days_until_renewal) VALUES
('N/A', 0),
('Jednom godišnje za vozila mlađa od 6 godina', 365),
('Svakih 6 mjeseci za vozila starosti 6-10 godina', 182),
('Svakih 3 mjeseca za vozila starija od 10 godina', 90);

-- Unos vrsta dokumenata
INSERT INTO document_types (name) VALUES
('Licenca'),
('Periodički pregled vozila'),
('Svjedodžba'),
('Tehnički pregled vozila'),
('Osiguranje vozila'),
('Ugovor o radu');

-- Unos dokumenata
INSERT INTO document_files (arhiva, name, number, renewal_date, renewal_period, service_provider, created_by, document_type_id, location_id, resource_type_id, status_id, vehicle_inspection_period_id) VALUES
(true, 'Benehaert R3 EKG aparat - Mindray', 'FK-26032761', '2024-04-22', 60, 'Unicomp d.o.o.', 1, 1, 1, 1, 2, 1),
(true, 'Mortara, ELI 230 EKG aparat - Mortara Instrument', '113080154080', '2024-04-22', 60, 'Unicomp d.o.o.', 1, 1, 1, 1, 2, 1),
(true, 'Aspirator na električni pogon', '40174', '2024-10-16', 60, 'Inel-medicinska tehnika d.o.o.', 1, 1, 1, 1, 2, 1),
(true, 'Lifepak 15 Defibrilator - Fiziokontrol', '47416358', '2024-11-05', 60, 'Elektroničar d.o.o.', 1, 1, 1, 1, 2, 1),
(true, 'Gučin K.', 'N/A', '2025-03-01', 60, 'N/A', 2, 3, 3, 2, 2, 1),
(true, 'Celjak M.', 'N/A', '2025-03-01', 60, 'N/A', 2, 3, 3, 2, 2, 1),
(true, 'Turk N', 'N/A', '2025-03-01', 60, 'N/A', 2, 3, 3, 2, 2, 1),
(true, 'Vozilo', 'KR 686 - IO', '2025-10-11', 60, 'AutoTech d.o.o.', 3, 4, 1, 3, 1, 1),
(true, 'Vozilo', 'KR 687 - IO', '2025-10-11', 60, 'AutoTech d.o.o.', 3, 4, 1, 3, 1, 1);
