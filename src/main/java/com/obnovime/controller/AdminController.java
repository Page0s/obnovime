package com.obnovime.controller;

import com.obnovime.model.AppUser;
import com.obnovime.model.DocumentType;
import com.obnovime.repository.AppUserRepository;
import com.obnovime.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @GetMapping("/admin")
    public String showAdmin(){
        return "AdminMainPage";
    }

    @GetMapping("/adminUsersList")
    public String showAdminUsersList(Model model){
        List<AppUser> users = appUserRepository.findAll(); // Dohvati sve korisnike iz baze
        model.addAttribute("appUser", users);
        return "AdminUsersList";
    }

    @GetMapping("/adminDocumentTypeList")
    public String showDocumentTypes(Model model){
        List<DocumentType> documentTypes = documentTypeRepository.findAll(); // Dohvati sve tipove dokumenata
        model.addAttribute("documentTypes", documentTypes);
        model.addAttribute("newDocumentType", new DocumentType());
        return "AdminDocumentTypeList";
    }

    @PostMapping("/adminDocumentTypeList/update") // Također mijenjamo endpoint
    public String updateDocumentType(@RequestParam Long id,
                                     @RequestParam String name,
                                     @RequestParam int renewalPeriod) {
        DocumentType documentType = documentTypeRepository.findById(id).orElse(null);
        if (documentType != null) {
            documentType.setName(name);
            documentType.setRenewalPeriod(renewalPeriod);
            documentTypeRepository.save(documentType);
        }
        return "redirect:/adminDocumentTypeList"; // Osvježi stranicu nakon izmjene
    }

    @PostMapping("/adminDocumentTypeList/add")
    public String addDocumentType(@ModelAttribute DocumentType newDocumentType) {
        documentTypeRepository.save(newDocumentType);
        return "redirect:/adminDocumentTypeList"; // Osvježi stranicu nakon dodavanja
    }

    @PostMapping("/adminDocumentTypeList/delete")
    public String deleteDocumentType(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            documentTypeRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Tip dokumenta uspješno obrisan.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom brisanja: " + e.getMessage());
        }
        return "redirect:/adminDocumentTypeList";
    }

    @PostMapping("/adminUsersList/update")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String email,
                             @RequestParam String phoneNumber,
                             @RequestParam String department,
                             RedirectAttributes redirectAttributes) {
        try {
            AppUser user = appUserRepository.findById(id).orElse(null);
            if (user != null) {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                user.setDepartment(department);
                appUserRepository.save(user);
                redirectAttributes.addFlashAttribute("success", "Korisnik uspješno ažuriran.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom ažuriranja korisnika: " + e.getMessage());
        }
        return "redirect:/adminUsersList";
    }

    @PostMapping("/adminUsersList/delete")
    public String deleteUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            appUserRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Korisnik uspješno obrisan.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom brisanja korisnika: " + e.getMessage());
        }
        return "redirect:/adminUsersList";
    }

    @PostMapping("/adminUsersList/add")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String phoneNumber,
                          @RequestParam String department,
                          RedirectAttributes redirectAttributes) {
        try {
            AppUser newUser = new AppUser();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setPhoneNumber((phoneNumber == null || phoneNumber.trim().isEmpty()) ? "-" : phoneNumber);
            newUser.setDepartment((department == null || department.trim().isEmpty()) ? "-" : department);
            newUser.setUserType("CLIENT");
            appUserRepository.save(newUser);

            redirectAttributes.addFlashAttribute("success", "Korisnik uspješno dodan.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom dodavanja korisnika: " + e.getMessage());
        }
        return "redirect:/adminUsersList";
    }
}
