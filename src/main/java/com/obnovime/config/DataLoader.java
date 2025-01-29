package com.obnovime.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.obnovime.model.DokumentFile;
import com.obnovime.repository.DocumentRepository;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private DocumentRepository documentRepository;

    public DataLoader(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void run(String... args) {
        // Only load data if repository is empty
        if (documentRepository.count() == 0) {
            loadSampleData();
        }
    }

    private void loadSampleData() {
        DokumentFile[] documents = {
            DokumentFile.builder()
                .name("Defiblirator")
                .number("OPR-2024-004")
                .renewalDate(LocalDate.of(2024, 12, 1))
                .renewalPeriod(60)
                .status("Nema obnove")
                .serviceProvider("TechServis d.o.o.")
                .location("Zagreb")
                .department("Glavna Sestra")
                .resourceType("Oprema")
                .documentType("Licenca")
                .arhiva(false)
                .build(),
            new DokumentFile(
                null, // id will be generated
                "Licenca za rad",
                "LIC-2024-001",
                LocalDate.of(2024, 12, 15),
                60,
                "Pokreni obnovu",
                "",
                "Zabok",
                "Glavna Sestra",
                "Radnik",
                "Licenca",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Registracija vozila",
                "REG-2024-002",
                LocalDate.of(2024, 12, 20),
                60,
                "Obnova u tijeku",
                "AutoTech d.o.o.",
                "Zagreb",
                "Vozni park",
                "Vozilo",
                "Registracija",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Periodički pregled vozila",
                "CERT-2024-003",
                LocalDate.of(2025, 2, 1),
                60,
                "Pokreni obnovu",
                "AutoTech d.o.o.",
                "Zagreb",
                "Vozni park",
                "Vozilo",
                "Periodički pregled vozila",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Respirator",
                "OPR-2024-004",
                LocalDate.of(2025, 2, 15),
                60,
                "Pokreni obnovu",
                "MedTech d.o.o.",
                "Zabok",
                "Glavna Sestra",
                "Oprema",
                "Licenca",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "EKG aparat",
                "OPR-2024-005",
                LocalDate.of(2025, 2, 20),
                60,
                "Pokreni obnovu",
                "MedTech d.o.o.",
                "Zabok",
                "Glavna Sestra",
                "Oprema",
                "Licenca",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Aspirator",
                "OPR-2024-006",
                LocalDate.of(2025, 2, 25),
                60,
                "Pokreni obnovu",
                "MedTech d.o.o.",
                "Zabok",
                "Glavna Sestra",
                "Oprema",
                "Licenca",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Ventilator",
                "OPR-2024-005",
                LocalDate.of(2025, 3, 1),
                0, // No renewal period for this one
                "Nema obnove",
                "MedTech d.o.o.",
                "Zabok",
                "Glavna Sestra",
                "Oprema",
                "Licenca",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Licenca za rad",
                "DOZ-2024-006",
                LocalDate.of(2025, 6, 30),
                60,
                "Aktivno",
                "",
                "Zabok",
                "Glavna Sestra",
                "Radnik",
                "Licenca",
                false
            )
        };

        documentRepository.saveAll(Arrays.asList(documents));
    }
}
