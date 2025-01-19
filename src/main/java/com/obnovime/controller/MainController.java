package com.obnovime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    
    @PostMapping("/main")
    public String handleLogin() {
        // For prototype, just redirect to main page
        return "ObnoviMe_Main";
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "ObnoviMe_Main";
    }

    @GetMapping("/form")
    public String showForm() {
        return "ObnoviMe_Form";
    }

    @GetMapping("/archive")
    public String showArchive() {
        return "ObnoviMe_Arhiva";
    }

    @GetMapping("/history")
    public String showHistory() {
        return "ObnoviMe_Povijest";
    }

    @GetMapping("/edit")
    public String showEditForm() {
        return "ObnoviMe_EditForm";
    }

    @GetMapping("/renewal")
    public String showRenewal() {
        return "ObnoviMe_Obnova";
    }

    @GetMapping("/info")
    public String showInfo() {
        return "ObnoviMe_Informacije";
    }
}
