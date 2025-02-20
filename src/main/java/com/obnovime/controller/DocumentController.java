package com.obnovime.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    public DocumentController(
            DocumentRepository documentRepository,
            DocumentStatusRepository documentStatusRepository,
            LocationRepository locationRepository,
            ResourceTypeRepository resourceTypeRepository) {
        this.documentRepository = documentRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.locationRepository = locationRepository;
        this.resourceTypeRepository = resourceTypeRepository;
    }

    private void updateDocumentStatus(DocumentFile document) {
            LocalDate today = LocalDate.now();
            LocalDate alertDate = document.getRenewalDate().minusDays(Optional.ofNullable(document.getRenewalPeriod()).orElse(0));

        DocumentStatus activeStatus = documentStatusRepository.findById(1L).orElseThrow(); // Aktivno
        DocumentStatus renewalStatus = documentStatusRepository.findById(2L).orElseThrow(); // Vrijeme za obnovu
        DocumentStatus renewalStatusExpired = documentStatusRepository.findById(6L).orElseThrow();
        DocumentStatus renewalInProgressExpired = documentStatusRepository.findById(5L).orElseThrow();

            if ((today.isAfter(alertDate) || today.equals(alertDate)) && 
                activeStatus.getName().equalsIgnoreCase(document.getStatus().getName())) {
                document.setStatus(renewalStatus);
                documentRepository.save(document);
            }

            if (today.isBefore(alertDate) && 
                renewalStatus.getName().equalsIgnoreCase(document.getStatus().getName())) {
                document.setStatus(activeStatus);
                documentRepository.save(document);

            if(today.isBefore(alertDate) &&
                    renewalStatusExpired.getName().equalsIgnoreCase(document.getStatus().getName())){
                document.setStatus(activeStatus);
                documentRepository.save(document);
            }

            if(today.isBefore(alertDate) &&
                    renewalInProgressExpired.getName().equalsIgnoreCase(document.getStatus().getName())){
                document.setStatus(activeStatus);
                documentRepository.save(document);
            }
        }
}

    @GetMapping("/main")
    public String showMainPage(Model model) {

        List<DocumentFile> documents = documentRepository.findAllByOrderByRenewalDateAsc();
        documents.forEach(this::updateDocumentStatus);

        List<DocumentFileDTO> documentDtos = documents.stream()
                .filter(Objects::nonNull)
                .map(DocumentFileDTO::fromEntity)
                .collect(Collectors.toList());

        model.addAttribute("documents", documentDtos);

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
            @RequestParam("statusId") Long statusId,
            @RequestParam("renewalDate") LocalDate renewalDate,
            @RequestParam(value = "arhiva", required = false, defaultValue = "false") Boolean arhiva,
            RedirectAttributes redirectAttributes) {
        
        Optional<DocumentFile> existingDoc = documentRepository.findById(id);
        
        if (existingDoc.isPresent()) {
            DocumentFile existing = existingDoc.get();

            System.out.println("DOCUMENT ID: " + existing.getId());
            System.out.println("DOCUMENT NAME: " + existing.getName());
            System.out.println("RENEWAL DATE: " + existing.getRenewalDate());
            System.out.println("NEW RENEWAL DATE: " + renewalDate);
            System.out.println("STATUS ID: " + existing.getStatus().getId());
            System.out.println("NEW STATUS ID: " + statusId);

            if (existing.getRenewalDate().isBefore(renewalDate)) {
                DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
                existing.setStatus(activeStatus);
                existing.setRenewalDate(renewalDate);
            } else if (existing.getRenewalDate().isEqual(renewalDate)) {
                DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
                existing.setStatus(activeStatus);
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
            @RequestParam("renewalPeriod") Integer renewalPeriod,
            @RequestParam("locationId") Long locationId,
            @RequestParam("resourceTypeId") Long resourceTypeId,
            @RequestParam("serviceProvider") String serviceProvider,
            RedirectAttributes redirectAttributes) {

        Optional<DocumentFile> existingDoc = documentRepository.findById(id);

        if (existingDoc.isPresent()) {
            DocumentFile dokument = existingDoc.get();
            dokument.setName(name);
            dokument.setRenewalPeriod(renewalPeriod);
            
            Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
            dokument.setLocation(location);
            
            ResourceType resourceType = resourceTypeRepository.findById(resourceTypeId)
                .orElseThrow(() -> new RuntimeException("Resource type not found"));
            dokument.setResourceType(resourceType);
            
            dokument.setServiceProvider(serviceProvider);     

            documentRepository.save(dokument);
            redirectAttributes.addFlashAttribute("successMessage", "Dokument uspješno uređen!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Greška: Dokument nije pronađen!");
        }

        return "redirect:/main";
    }
    @GetMapping("/filter")
    public String filterDocuments(
            @RequestParam(name = "documentTypes", required = false) List<String> documentTypes,
            @RequestParam(name = "resourceTypeName", required = false) List<String> resourceTypeName,
            @RequestParam(name = "statusName", required = false) List<String> statusName,
            @RequestParam(name = "locationName", required = false) List<String> locationName,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, name = "clear") String clear,
            Model model
    ) {
        if ("true".equals(clear)) {
            // Vrati punu listu
            List<DocumentFile> allDocs = documentRepository.findAllByOrderByRenewalDateAsc();
            model.addAttribute("documents", allDocs);

            // ... i postavi docTypes, resourceTypes i sve ostalo na null (ili prazno)
            documentTypes = null;
            resourceTypeName = null;
            statusName = null;
            locationName = null;
            startDate = null;
            endDate = null;
        } else {
            // Inače, filtriraj
        // Poziv metode u DocumentRepository koja radi filtriranje
        List<DocumentFile> filteredDocs = documentRepository.searchDocuments(
                documentTypes,
                resourceTypeName,
                statusName,
                locationName,
                startDate,
                endDate
        );
            model.addAttribute("documents", filteredDocs);
        List<DocumentFileDTO> documentDtos = filteredDocs.stream()
                .map(DocumentFileDTO::fromEntity)
                .collect(Collectors.toList());

        model.addAttribute("documents", documentDtos);
            model.addAttribute("selectedDocumentTypes", documentTypes);
            model.addAttribute("selectedResourceTypes", resourceTypeName);
            model.addAttribute("selectedStatuses", statusName);
            model.addAttribute("selectedLocations", locationName);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            System.out.println("docTypes = " + documentTypes);
        }
        return "DocumentMainForm";
    }
}
