package com.obnovime.controller;

import com.obnovime.model.*;
import com.obnovime.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
public class FormController {
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final LocationRepository locationRepository;
    private final ResourceTypeRepository resourceTypeRepository;
    private final DocumentStatusRepository documentStatusRepository;
    private final AppUserRepository userRepository;

    public FormController(
            DocumentRepository documentRepository,
            DocumentTypeRepository documentTypeRepository,
            LocationRepository locationRepository,
            ResourceTypeRepository resourceTypeRepository,
            DocumentStatusRepository documentStatusRepository,
            AppUserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.locationRepository = locationRepository;
        this.resourceTypeRepository = resourceTypeRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/saveDocument")
    public String saveDocument(
            @RequestParam String name,
            @RequestParam String identificationNumber,
            @RequestParam LocalDate renewalDate,
            @RequestParam String service,
            @RequestParam("locationId") Long locationId,
            @RequestParam("resourceTypeId") Long resourceTypeId,
            @RequestParam("documentTypeId") Long documentTypeId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(name);
            document.setNumber(identificationNumber);
            document.setRenewalDate(renewalDate);
            document.setServiceProvider(service);

            // Dohvati lokaciju
            Location location = locationRepository.findById(locationId)
                    .orElseThrow(() -> new RuntimeException("Location not found"));
            document.setLocation(location);

            // Dohvati resurs
            ResourceType resourceType = resourceTypeRepository.findById(resourceTypeId)
                    .orElseThrow(() -> new RuntimeException("ResourceType not found"));
            document.setResourceType(resourceType);

            // Dohvati i postavi tip dokumenta
            DocumentType documentType = documentTypeRepository.findById(documentTypeId)
                    .orElseThrow(() -> new RuntimeException("DocumentType not found"));
            document.setDocumentType(documentType);

            // Postavi korisnika koji kreira dokument iz sesije
            AppUser currentUser = (AppUser) session.getAttribute("user");
            if (currentUser != null) {
                document.setCreatedBy(currentUser);
            } else {
                throw new RuntimeException("User not found in session");
            }

            // Postavi početni status na "Aktivno"
            DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
            document.setStatus(activeStatus);

            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/error";
        }
    }






}
