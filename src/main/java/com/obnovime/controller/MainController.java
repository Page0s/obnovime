package com.obnovime.controller;

import com.obnovime.model.*;
import com.obnovime.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.obnovime.repository.LocationRepository;

import java.util.List;

@Controller
public class MainController {

    private final DocumentRepository documentRepository;
    private final DocumentStatusRepository documentStatusRepository;
    private final LocationRepository locationRepository;
    private final ResourceTypeRepository resourceTypeRepository;
    private final DocumentTypeRepository documentTypeRepository; // ✅ Dodano
    private final AppUserRepository userRepository;

    public MainController(DocumentRepository documentRepository, DocumentStatusRepository documentStatusRepository,
                          LocationRepository locationRepository, ResourceTypeRepository resourceTypeRepository,
                          DocumentTypeRepository documentTypeRepository, AppUserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.locationRepository = locationRepository;
        this.resourceTypeRepository = resourceTypeRepository;
        this.documentTypeRepository = documentTypeRepository; // ✅ Inicijalizirano
        this.userRepository = userRepository;
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        List<Location> locations = locationRepository.findAll();
        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        List<DocumentStatus> documentStatuses = documentStatusRepository.findAll();
        List<ResourceType> resourceTypes = resourceTypeRepository.findAll();
        List<AppUser> users = userRepository.findByUserType("CLIENT");

        // Kreiranje novog dokumenta i postavljanje praznog documentType kako bi se izbjegao null
        DocumentFile document = new DocumentFile();
        document.setDocumentType(new DocumentType());

        model.addAttribute("locations", locations);
        model.addAttribute("document_types", documentTypes);
        model.addAttribute("document_statuses", documentStatuses);
        model.addAttribute("resource_types", resourceTypes);
        model.addAttribute("document", document); // Dodaj dokument u model
        model.addAttribute("app_users", users);

        return "DocumentEntryForm";
    }




    @GetMapping("/archive")
    public String showArchive() {
        return "DocumentArchive";
    }

    @GetMapping("/history")
    public String showHistory() {
        return "DocumentRenewalHistory";
    }

    @GetMapping("/index")
    public String showIndex() {
        return "redirect:/index.html";
    }
}
