package com.obnovime.service;

import com.obnovime.model.DocumentFile;
import com.obnovime.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentExpirationService {

    private final DocumentRepository documentRepository;
    private final EmailService emailService;

    @Value("${app.notification.email}")
    private String notificationEmail;

    public DocumentExpirationService(DocumentRepository documentRepository, EmailService emailService) {
        this.documentRepository = documentRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 8 * * *") // Runs at 8:00 AM every day
    public void checkDocumentExpirations() {
        LocalDate today = LocalDate.now();
        List<DocumentFile> allDocuments = documentRepository.findAllByOrderByRenewalDateAsc();
        
        System.out.println("Checking document expirations. Total documents: " + allDocuments.size());
        System.out.println("Today's date: " + today);

        // Documents that will expire in specific number of days
        List<DocumentFile> expiringInXDays = allDocuments.stream()
            .filter(doc -> {
                LocalDate alertDate = doc.getRenewalDate().minusDays(
                    doc.getDocumentType() != null ? doc.getDocumentType().getRenewalPeriod() : 0
                );
                return alertDate.equals(today);
            })
            .collect(Collectors.toList());

        System.out.println("Documents expiring in X days: " + expiringInXDays.size());

        if (!expiringInXDays.isEmpty()) {
            System.out.println("Sending notification for documents entering renewal period");
            emailService.sendDocumentExpirationNotification(
                notificationEmail,
                expiringInXDays,
                "Dokumenti ulaze u period obnove"
            );
        }

        // Documents that will expire in 10 days
        List<DocumentFile> expiringIn10Days = allDocuments.stream()
            .filter(doc -> doc.getRenewalDate().minusDays(10).equals(today))
            .collect(Collectors.toList());

        System.out.println("Documents expiring in 10 days: " + expiringIn10Days.size());

        if (!expiringIn10Days.isEmpty()) {
            System.out.println("Sending notification for documents expiring in 10 days");
            emailService.sendDocumentExpirationNotification(
                notificationEmail,
                expiringIn10Days,
                "Dokumenti ističu za 10 dana"
            );
        }

        // Documents expiring today
        List<DocumentFile> expiringToday = allDocuments.stream()
            .filter(doc -> doc.getRenewalDate().equals(today))
            .collect(Collectors.toList());

        System.out.println("Documents expiring today: " + expiringToday.size());

        if (!expiringToday.isEmpty()) {
            System.out.println("Sending notification for documents expiring today");
            emailService.sendDocumentExpirationNotification(
                notificationEmail,
                expiringToday,
                "Dokumenti ističu danas"
            );
        }
    }
}
