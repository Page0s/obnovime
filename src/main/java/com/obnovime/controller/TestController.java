package com.obnovime.controller;

import com.obnovime.service.DocumentExpirationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final DocumentExpirationService documentExpirationService;

    public TestController(DocumentExpirationService documentExpirationService) {
        this.documentExpirationService = documentExpirationService;
    }

    @GetMapping("/check-documents")
    public String testDocumentCheck() {
        documentExpirationService.checkDocumentExpirations();
        return "Document check triggered successfully!";
    }
}
