package com.obnovime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.obnovime.model.*;
import com.obnovime.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        if ((today.isAfter(alertDate) || today.equals(alertDate)) && activeStatus.getName().equals(document.getStatus().getName())) {
            document.setStatus(renewalStatus);
            documentRepository.save(document);
        }

        if (today.isBefore(alertDate) && renewalStatus.getName().equals(document.getStatus().getName())) {
            document.setStatus(activeStatus);
            documentRepository.save(document);
        }
    }

    @GetMapping("/main")
    public String showMainPage(Model model) {
        List<DocumentFile> documents = documentRepository.findAllByOrderByRenewalDateAsc();
        documents.forEach(this::updateDocumentStatus);
        model.addAttribute("documents", documents);
        
        documents.forEach(document -> {
            System.out.println("ID: " + document.getId() + " DATE: ");
            System.out.println("Name: " + Optional.ofNullable(document.getRenewalDate()).orElse(LocalDate.MIN));
            System.out.println("Name: " + Optional.ofNullable(document.getName()).orElse("N/A"));
            System.out.println("Number: " + Optional.ofNullable(document.getNumber()).orElse("N/A"));
            System.out.println("Status: " + Optional.ofNullable(document.getStatus()).map(DocumentStatus::getName).orElse("N/A"));
            System.out.println("Location: " + Optional.ofNullable(document.getLocation()).map(Location::getName).orElse("N/A"));

            System.out.println("Resource Type: " + Optional.ofNullable(document.getResourceType()).map(ResourceType::getName).orElse("N/A"));
            System.out.println("Service Provider: " + Optional.ofNullable(document.getServiceProvider()).orElse("N/A"));
            System.out.println("Renewal Period: " + Optional.ofNullable(document.getRenewalPeriod()).orElse(0));
            System.out.println("Archive: " + Optional.ofNullable(document.getArhiva()).orElse(false));
            System.out.println("Document Type: " + Optional.ofNullable(document.getDocumentType()).map(DocumentType::getName).orElse("N/A"));
            System.out.println("Created By: " + Optional.ofNullable(document.getCreatedBy()).map(AppUser::getEmail).orElse("N/A"));
        });

        return "redirect:/index.html";
        // return "DocumentMainForm";
    }

    @GetMapping("/dokument/{id}/obnova")
    public String showRenewalForm(@PathVariable Long id, Model model) {
        Optional<DocumentFile> document = documentRepository.findById(id);
        
        if (document.isPresent()) {
            model.addAttribute("dokument", document.get());
            model.addAttribute("statuses", documentStatusRepository.findAll());
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

            // if (existing.getRenewalDate().isBefore(renewalDate)) {
            //     DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
            //     existing.setStatus(activeStatus);
            //     existing.setRenewalDate(renewalDate);
            // } else if (existing.getRenewalDate().isEqual(renewalDate)) {
            //     existing.setStatus(newStatus);
            //     existing.setArhiva(arhiva);
            // }
            
            // existing.setUpdatedAt(LocalDateTime.now());
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
}
