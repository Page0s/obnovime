package com.obnovime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/form")
    public String showForm() {
        return "ObnoviMe_Form";
    }

    @GetMapping("/archive")
    public String showArchive() {
        return "ObnoviMe_Archive";
    }

    @GetMapping("/history")
    public String showHistory() {
        return "ObnoviMe_RenewalHistory";
    }

    @GetMapping("/index")
    public String showIndex() {
        return "redirect:/index.html";
    }

}
