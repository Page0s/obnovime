package com.obnovime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/form")
    public String showForm() {
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
