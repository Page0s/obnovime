package com.obnovime.controller;

import com.obnovime.model.AppUser;
import com.obnovime.model.DocumentType;
import com.obnovime.repository.AppUserRepository;
import com.obnovime.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String showAdminUserList(Model model){
        List<AppUser> users = appUserRepository.findAll(); // Dohvati sve korisnike iz baze
        model.addAttribute("appUser", users);
        return "AdminUsersList";
    }

    @GetMapping("/adminDocumentTypeList")
    public String showDocumentTypes(Model model){
        List<DocumentType> documentTypes = documentTypeRepository.findAll(); // Dohvati sve tipove dokumenata
        model.addAttribute("documentTypes", documentTypes);
        return "AdminDocumentTypeList";
    }
}
