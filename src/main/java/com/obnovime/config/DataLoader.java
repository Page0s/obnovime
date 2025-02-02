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
                "Tehnički pregled vozila",
                "KR 686 - IO",
                LocalDate.of(2025, 10, 11),
                15,
                "Aktivno",
                "AutoTech d.o.o.",
                "Krapina",
                "Vozni park",
                "Vozilo",
                "Tehnički pregled vozila",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Tehnički pregled vozila",
                "KR 687 - IO",
                LocalDate.of(2025, 10, 11),
                15,
                "Aktivno",
                "AutoTech d.o.o.",
                "Krapina",
                "Vozni park",
                "Vozilo",
                "Tehnički pregled vozila",
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
                "Gučin K.",
                "",
                LocalDate.of(2025, 3, 1),
                60,
                "Pokreni obnovu",
                "",
                "Zabok",
                "Glavna Sestra",
                "Radnik",
                "Svjedodžba",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Celjak M.",
                "",
                LocalDate.of(2025, 3, 1),
                60,
                "Pokreni obnovu",
                "",
                "Zabok",
                "Glavna Sestra",
                "Radnik",
                "Svjedodžba",
                false
            ),
            new DokumentFile(
                null, // id will be generated
                "Turk N",
                "",
                LocalDate.of(2025, 3, 1),
                60,
                "Pokreni obnovu",
                "",
                "Zabok",
                "Glavna Sestra",
                "Radnik",
                "Svjedodžba",
                false
            )
        };

        documentRepository.saveAll(Arrays.asList(documents));
    }
}
