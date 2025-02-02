package com.obnovime.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.obnovime.model.DokumentFile;
import com.obnovime.repository.DocumentRepository;

import javax.swing.text.Document;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class DocumentController {

    private final DocumentRepository documentRepository;

    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping("/main")
    public String showDocument(Model model) {
        model.addAttribute("documents", documentRepository.findAllByOrderByRenewalDateAsc());
        return "ObnoviMe_Main";
    }

    @GetMapping("/dokument/{id}/obnova")
    public String showRenewalForm(@PathVariable Long id, Model model) {
        System.out.println("Received ID: " + id);
        Optional<DokumentFile> document = documentRepository.findById(id);
        
        if (document.isPresent()) {
            model.addAttribute("dokument", document.get());
            return "ObnoviMe_Renewal";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/spremi-obnovu")
    public String handleRenewal(@RequestParam("id") Long id,
                              @RequestParam("status") String status,
                              @RequestParam("renewalDate") LocalDate renewalDate,
                              @RequestParam(value = "arhiva", required = false, defaultValue = "false") Boolean arhiva,
                              RedirectAttributes redirectAttributes) {
        
        Optional<DokumentFile> existingDoc = documentRepository.findById(id);
        
        if (existingDoc.isPresent()) {
            DokumentFile existing = existingDoc.get();

            // Update fields including archive status
            existing.setStatus(status);
            existing.setRenewalDate(renewalDate);
            existing.setArhiva(arhiva);
            
            documentRepository.save(existing);
            
            redirectAttributes.addFlashAttribute("message", "Dokument uspješno ažuriran");
        } else {
            redirectAttributes.addFlashAttribute("error", "Dokument nije pronađen");
        }
        
        return "redirect:/main";
    }

    @PostMapping("/saveDocument")
    public String saveDocument( RedirectAttributes redirectAttributes) {
        // Logika za spremanje dokumenta u bazu podataka ili drugu obradu

        // Dodavanje informacije za prikaz toast poruke
        redirectAttributes.addFlashAttribute("showToast", true);

        // Redirekcija na glavnu stranicu
        return "redirect:/main";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        Optional<DokumentFile> dokument = documentRepository.findById(id);

        if (dokument.isPresent()) {
            session.setAttribute("editDokument", dokument.get()); // Pohrana u sesiju
            model.addAttribute("dokument", dokument.get());
            return "DocumentEditForm"; // Prikaz forme
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/updateDocument")
    public String updateDocument(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("renewalPeriod") Integer renewalPeriod,  // Broj dana za podsjetnik
            @RequestParam("location") String location,             // Ispostava
            @RequestParam("serviceProvider") String serviceProvider, // Serviser
            RedirectAttributes redirectAttributes, HttpSession session) {

        Optional<DokumentFile> existingDoc = documentRepository.findById(id);

        if (existingDoc.isPresent()) {
            DokumentFile dokument = existingDoc.get();
            dokument.setName(name);
            dokument.setRenewalPeriod(renewalPeriod);
            dokument.setLocation(location);
            dokument.setServiceProvider(serviceProvider);

            documentRepository.save(dokument);
            session.removeAttribute("editDokument");

            redirectAttributes.addFlashAttribute("successMessage", "Dokument uspješno uređen!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Greška: Dokument nije pronađen!");
        }

        return "redirect:/main";
    }






}
