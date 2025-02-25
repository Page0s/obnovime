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
            System.out.println("Pokušaj slanja e-maila na: " + toEmail);
            System.out.println("Vrsta upozorenja: " + alertType);
            System.out.println("Broj dokumenata: " + documents.size());
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            // Split email addresses by comma and remove any whitespace
            String[] recipients = toEmail.split(",");
            for (int i = 0; i < recipients.length; i++) {
                recipients[i] = recipients[i].trim();
            }
            helper.setTo(recipients);
            helper.setSubject("Upozorenje o isteku dokumenta - " + alertType);

            Context context = new Context();
            context.setVariable("documents", documents);
            context.setVariable("alertType", alertType);
            context.setVariable("appUrl", "https://obnovime-develop.up.railway.app/");

            String emailContent = templateEngine.process("email/document-expiration", context);
            helper.setText(emailContent, true);

            System.out.println("Slanje e-maila...");
            mailSender.send(message);
            System.out.println("E-mail uspješno poslan!");
        } catch (MessagingException e) {
            System.err.println("Neuspjelo slanje e-maila: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Neuspjelo slanje e-mail obavijesti", e);
        }
    }
}
