package com.obnovime.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.obnovime.model.*;
import com.obnovime.repository.*;
import com.obnovime.dto.DocumentFileDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import java.util.Objects;

@Controller
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final DocumentStatusRepository documentStatusRepository;
    private final LocationRepository locationRepository;
    private final ResourceTypeRepository resourceTypeRepository;
    private final RenewalHistoryRepository renewalHistoryRepository;

    public DocumentController(
            DocumentRepository documentRepository,
            DocumentStatusRepository documentStatusRepository,
            LocationRepository locationRepository,
            ResourceTypeRepository resourceTypeRepository, RenewalHistoryRepository renewalHistoryRepository) {
        this.documentRepository = documentRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.locationRepository = locationRepository;
        this.resourceTypeRepository = resourceTypeRepository;
        this.renewalHistoryRepository = renewalHistoryRepository;
    }

    private void updateDocumentStatus(DocumentFile document) {
        LocalDate today = LocalDate.now();
        LocalDate renewalDate = document.getRenewalDate();
        LocalDate alertDate = renewalDate.minusDays(
            Optional.ofNullable(document.getDocumentType().getRenewalPeriod()).orElse(0)
        );

        DocumentStatus activeStatus = documentStatusRepository.findById(1L).orElseThrow(); // Aktivno
        DocumentStatus renewalStatus = documentStatusRepository.findById(2L).orElseThrow(); // Vrijeme za obnovu
        DocumentStatus renewalInProgress = documentStatusRepository.findById(4L).orElseThrow(); // Obnova u tijeku
        DocumentStatus renewalStatusExpired = documentStatusRepository.findById(6L).orElseThrow(); // Vrijeme za obnovu isteklo
        DocumentStatus renewalInProgressExpired = documentStatusRepository.findById(5L).orElseThrow(); // Obnova u tijeku isteklo

        // If we're between alert date and renewal date, and status is Active -> set to Renewal
        if (!today.isBefore(alertDate) && today.isBefore(renewalDate) && 
            activeStatus.getName().equalsIgnoreCase(document.getStatus().getName())) {
            document.setStatus(renewalStatus);
            documentRepository.save(document);
        }
        
        // If we're before alert date and status is Renewal -> set back to Active
        if (today.isBefore(alertDate)) {
            if (renewalStatus.getName().equalsIgnoreCase(document.getStatus().getName()) ||
                renewalStatusExpired.getName().equalsIgnoreCase(document.getStatus().getName()) ||
                renewalInProgressExpired.getName().equalsIgnoreCase(document.getStatus().getName())) {
                document.setStatus(activeStatus);
                documentRepository.save(document);
            }
        }

        // If we're after renewal date and status is Renewal -> set to Expired
        if (today.isAfter(renewalDate)) {
            if (renewalStatus.getName().equalsIgnoreCase(document.getStatus().getName())) {
                document.setStatus(renewalStatusExpired);
                documentRepository.save(document);
            }
        }

                // If we're after renewal date and status is Renewal -> set to Expired
        if (today.isAfter(renewalDate)) {
            if (renewalStatus.getName().equalsIgnoreCase(document.getStatus().getName())) {
                document.setStatus(renewalStatusExpired);
                documentRepository.save(document);
            }
        }

        if (today.isAfter(renewalDate)) {
            if (renewalInProgress.getName().equalsIgnoreCase(document.getStatus().getName())) {
                document.setStatus(renewalInProgressExpired);
                documentRepository.save(document);
            }
        }
    }

    @GetMapping("/main")
    public String showMainPage(
            Model model,
            // Paginacija
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            // Filtriranje
            @RequestParam(name = "documentTypes", required = false) List<String> documentTypes,
            @RequestParam(name = "resourceTypeName", required = false) List<String> resourceTypeName,
            @RequestParam(name = "statusName", required = false) List<String> statusName,
            @RequestParam(name = "locationName", required = false) List<String> locationName,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, name = "clear") String clear
    ) {
        // 1) Ako je "clear" => resetiraj filter
        if ("true".equals(clear)) {
            documentTypes = null;
            resourceTypeName = null;
            statusName = null;
            locationName = null;
            startDate = null;
            endDate = null;
        }

        // 2) PageRequest
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("renewalDate").ascending());

        // 3) Ako su filter parametri prazni, uzmi sve (paginirano):
        if ((documentTypes == null || documentTypes.isEmpty())
                && (resourceTypeName == null || resourceTypeName.isEmpty())
                && (statusName == null || statusName.isEmpty())
                && (locationName == null || locationName.isEmpty())
                && startDate == null && endDate == null)
        {
            // Vrati sve, ali paginirano
            Page<DocumentFile> documentsPage = documentRepository.findAll(pageRequest);

            documentsPage.forEach(this::updateDocumentStatus);

            // Konverzija u DTO
            List<DocumentFileDTO> documentDtos = documentsPage.getContent().stream()
                    .map(DocumentFileDTO::fromEntity)
                    .toList();

            model.addAttribute("documents", documentDtos);
            model.addAttribute("currentPage", documentsPage.getNumber());
            model.addAttribute("totalPages", documentsPage.getTotalPages());
        } else {
            // 4) Ako su filter parametri postavljeni, filtriraj i paginiraj
            List<DocumentFile> filteredAll = documentRepository.searchDocuments(
                    documentTypes, resourceTypeName, statusName, locationName, startDate, endDate
            );
            filteredAll.forEach(this::updateDocumentStatus);

            // "Paginacija na listi"
            int startIdx = page * size;
            int endIdx = Math.min(startIdx + size, filteredAll.size());
            // Ako je startIdx >= veličine, to znači da je stranica prazna
            List<DocumentFile> pageContent = startIdx < filteredAll.size()
                    ? filteredAll.subList(startIdx, endIdx)
                    : List.of();

            // Konvertiraj sublistu u DTO
            List<DocumentFileDTO> documentDtos = pageContent.stream()
                    .map(DocumentFileDTO::fromEntity)
                    .toList();

            // Izračun "totalPages"
            int totalPages = (int) Math.ceil((double) filteredAll.size() / size);

            // Dodaj u model
            model.addAttribute("documents", documentDtos);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
        }

        // 5) Uvijek vrati i odabrane parametre, da ih Thymeleaf "zapamti" na formi:
        model.addAttribute("selectedDocumentTypes", documentTypes);
        model.addAttribute("selectedResourceTypes", resourceTypeName);
        model.addAttribute("selectedStatuses", statusName);
        model.addAttribute("selectedLocations", locationName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "DocumentMainForm";
    }

    @GetMapping("/document/{id}/obnova")
    public String showRenewalForm(@PathVariable Long id, Model model) {
        Optional<DocumentFile> documentOpt = documentRepository.findById(id);

        if (documentOpt.isPresent()) {
            DocumentFile document = documentOpt.get();
            System.out.println("ID OF THE DOCUMENT: " + document.getId());
            System.out.println("NAME OF THE DOCUMENT: " + document.getName());
            List<DocumentStatus> statuses = documentStatusRepository.findAll().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            // remove if from this list elements "Obnova u tijeku isteklo", "Vrijeme za obnovu isteklo", "Nema obnove", "Aktivno"
            statuses.removeIf(status -> status.getName().equals("Obnova u tijeku isteklo") ||
            status.getName().equals("Aktivno") ||
            status.getName().equals("Nema obnove") ||
            status.getName().equals("Vrijeme za obnovu isteklo"));

            model.addAttribute("document", document);
            model.addAttribute("statuses", statuses);
            return "DocumentRenewal";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/spremi-obnovu")
    public String handleRenewal(
            @RequestParam("id") Long id,
            @RequestParam(value = "statusId", required = false) Long statusId,
            @RequestParam(value = "renewalDate", required = false) LocalDate renewalDate,
            @RequestParam(value = "arhiva", required = false, defaultValue = "false") Boolean arhiva,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Optional<DocumentFile> existingDoc = documentRepository.findById(id);
        
        if (existingDoc.isPresent()) {
            DocumentFile existing = existingDoc.get();

            // Dohvati trenutno prijavljenog korisnika iz sesije
            AppUser currentUser = (AppUser) session.getAttribute("user");

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute("error", "Greška: Nema prijavljenog korisnika.");
                return "redirect:/main";
            }

            if (renewalDate != null && existing.getRenewalDate().isBefore(renewalDate)) {
                RenewalHistory renewalHistory = new RenewalHistory();
                renewalHistory.setDocumentFile(existing);
                renewalHistory.setRenewedBy(currentUser);
                renewalHistory.setPastRenewalDate(existing.getRenewalDate()); // Spremi trenutni datum obnove prije promjene

                renewalHistoryRepository.save(renewalHistory);

                DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
                existing.setStatus(activeStatus);
                existing.setRenewalDate(renewalDate);
            }



            if (renewalDate != null && existing.getRenewalDate().isBefore(renewalDate)) {
                DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
                existing.setStatus(activeStatus);
                existing.setRenewalDate(renewalDate);
            }

            if (statusId != null) {
                if (existing.getStatus().getId() != statusId) {
                    Optional<DocumentStatus> newStatus = documentStatusRepository.findById(statusId);
                    existing.setStatus(newStatus.get());
                    existing.setArhiva(arhiva);
                }
            }

            if (existing.getArhiva() != arhiva){
                existing.setArhiva(arhiva);
            }
            
            documentRepository.save(existing);
            
            redirectAttributes.addFlashAttribute("message", "Dokument uspješno ažuriran");
        } else {
            redirectAttributes.addFlashAttribute("error", "Dokument nije pronađen");
        }
        
        return "redirect:/main";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<DocumentFile> dokument = documentRepository.findById(id);

        if (dokument.isPresent()) {
            model.addAttribute("dokument", dokument.get());
            model.addAttribute("locations", locationRepository.findAll());
            model.addAttribute("resourceTypes", resourceTypeRepository.findAll());
            return "DocumentEditForm";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/updateDocument")
    public String updateDocument(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("serviceProvider") String serviceProvider,
            @RequestParam("locationId") Long locationId,
            RedirectAttributes redirectAttributes) {

        Optional<DocumentFile> existingDoc = documentRepository.findById(id);

        if (existingDoc.isPresent()) {
            DocumentFile dokument = existingDoc.get();
            dokument.setName(name);
            dokument.setServiceProvider(serviceProvider);
            
            Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
            dokument.setLocation(location);

            
            documentRepository.save(dokument);
            redirectAttributes.addFlashAttribute("successMessage", "Dokument uspješno uređen!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Greška: Dokument nije pronađen!");
        }

        return "redirect:/main";
    }
}
