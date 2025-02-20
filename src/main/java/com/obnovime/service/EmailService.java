package com.obnovime.service;

import com.obnovime.model.DocumentFile;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    @Value("${spring.mail.from}")
    private String fromEmail;
    
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendDocumentExpirationNotification(String toEmail, List<DocumentFile> documents, String alertType) {
        try {
            System.out.println("Attempting to send email to: " + toEmail);
            System.out.println("Alert type: " + alertType);
            System.out.println("Number of documents: " + documents.size());
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Document Expiration Alert - " + alertType);

            Context context = new Context();
            context.setVariable("documents", documents);
            context.setVariable("alertType", alertType);
            context.setVariable("appUrl", "https://obnovime-develop.up.railway.app/");

            String emailContent = templateEngine.process("email/document-expiration", context);
            helper.setText(emailContent, true);

            System.out.println("Sending email...");
            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to send email notification", e);
        }
    }
}
