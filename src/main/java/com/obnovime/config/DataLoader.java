package com.obnovime.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.obnovime.model.DocumentFile;
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
        DocumentFile[] documents = {
            DocumentFile.builder()
                .name("Aspirator na električni pogon")
                .number("40174")
                .renewalDate(LocalDate.of(2024, 10, 16))
                .renewalPeriod(30)
                .status("Aktivno")
                .serviceProvider("Inel-medicinska tehnika d.o.o.")
                .location("")
                .department("Glavna Sestra")
                .resourceType("Oprema")
                .documentType("Licenca")
                .arhiva(false)
                .build(),
            DocumentFile.builder()
                .name("Benehaert R3 EKG aparat - Mindray")
                .number("FK-26032761")
                .renewalDate(LocalDate.of(2024, 04, 22))
                .renewalPeriod(30)
                .status("Aktivno")
                .serviceProvider("Unicomp d.o.o.")
                .location("")
                .department("Glavna Sestra")
                .resourceType("Oprema")
                .documentType("Licenca")
                .arhiva(false)
                .build(),
            new DocumentFile(
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
            new DocumentFile(
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
                DocumentFile.builder()
                .name("Lifepak 15 Defibrilator - Fiziokontrol")
                .number("47416358")
                .renewalDate(LocalDate.of(2024, 11, 05))
                .renewalPeriod(30)
                .status("Aktivno")
                .serviceProvider("Elektroničar d.o.o.")
                .location("")
                .department("Glavna Sestra")
                .resourceType("Oprema")
                .documentType("Licenca")
                .arhiva(false)
                .build(),
                DocumentFile.builder()
                .name("Mortara, ELI 230 EKG aparat - Mortara Instrument")
                .number("113080154080")
                .renewalDate(LocalDate.of(2024, 04, 22))
                .renewalPeriod(30)
                .status("Aktivno")
                .serviceProvider("Unicomp d.o.o.")
                .location("")
                .department("Glavna Sestra")
                .resourceType("Oprema")
                .documentType("Licenca")
                .arhiva(false)
                .build(),
            new DocumentFile(
                null, // id will be generated
                "Gučin K.",
                "",
                LocalDate.of(2025, 3, 1),
                60,
                "Vrijeme za obnovu",
                "",
                "Zabok",
                "Glavna Sestra",
                "Radnik",
                "Svjedodžba",
                false
            ),
            new DocumentFile(
                null, // id will be generated
                "Celjak M.",
                "",
                LocalDate.of(2025, 3, 1),
                60,
                    "Vrijeme za obnovu",
                "",
                "Zabok",
                "Glavna Sestra",
                "Radnik",
                "Svjedodžba",
                false
            ),
            new DocumentFile(
                null, // id will be generated
                "Turk N",
                "",
                LocalDate.of(2025, 3, 1),
                60,
                    "Vrijeme za obnovu",
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
