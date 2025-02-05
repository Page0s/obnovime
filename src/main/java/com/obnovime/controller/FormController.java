package com.obnovime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.obnovime.model.DocumentFile;
import com.obnovime.repository.DocumentRepository;
import com.obnovime.dto.*;

@Controller
public class FormController {

    private final DocumentRepository documentRepository;

    public FormController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @PostMapping("/saveLicense")
    public String saveLicense(@ModelAttribute LicenseDTO licenseDTO, RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(licenseDTO.getName());
            document.setNumber(licenseDTO.getIdentificationNumber());
            document.setRenewalDate(licenseDTO.getRenewalDate());
            document.setServiceProvider(licenseDTO.getService());
            document.setLocation(licenseDTO.getOutpost());
            document.setDepartment(licenseDTO.getDepartment());
            document.setResourceType(licenseDTO.getResourceType());
            document.setRenewalPeriod(Integer.parseInt(licenseDTO.getReminderDay()));
            // Default value for this document type
            document.setDocumentType("Licenca");
            document.setStatus("Aktivno");
            document.setArhiva(false);
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/form";
        }
    }

    @PostMapping("/savePeriodicCheck")
    public String savePeriodicCheck(@ModelAttribute PeriodicCheckDTO periodicCheckDTO, RedirectAttributes redirectAttributes) {
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        System.out.println("Entering savePeriodicCheck method!");
        try {
            DocumentFile document = new DocumentFile();
            document.setName(periodicCheckDTO.getNamePeriodic());
            document.setNumber(periodicCheckDTO.getRegistrationPeriodic());
            document.setRenewalDate(periodicCheckDTO.getRenewalDatePeriodic());
            document.setServiceProvider(periodicCheckDTO.getServicePeriodic());
            document.setLocation(periodicCheckDTO.getOutpostPeriodic());
            document.setRenewalPeriod(Integer.parseInt(periodicCheckDTO.getReminderDayPeriodic()));
            // Default value for this document type
            document.setDocumentType("Periodički pregled vozila");
            document.setStatus("Aktivno");
            document.setArhiva(false);
            document.setDepartment("Vozni Park");
            document.setResourceType("Vozilo");
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/form";
        }
    }

    @PostMapping("/saveCertificate")
    public String saveCertificate(@ModelAttribute CertificateDTO certificateDTO, RedirectAttributes redirectAttributes) {
        System.out.println("Entering saveCertificate method!");
        try {
            System.out.println("Attempting to save certificate with name: " + certificateDTO.getNameCertificate() + " " + certificateDTO.getSurnnameCertificate());
            
            DocumentFile document = new DocumentFile();
            document.setName(certificateDTO.getNameCertificate() + " " + certificateDTO.getSurnnameCertificate());
            document.setNumber(certificateDTO.getIdentificationNumberCertificate());
            document.setRenewalDate(certificateDTO.getRenewalDateCertificate());
            document.setLocation(certificateDTO.getOutpostCertificate());
            
            // Ensure reminderDay is properly parsed to Integer
            try {
                document.setRenewalPeriod(Integer.parseInt(String.valueOf(certificateDTO.getReminderDayCertificate())));
            } catch (NumberFormatException e) {
                System.err.println("Error parsing reminder day: " + certificateDTO.getReminderDayCertificate());
                throw new RuntimeException("Invalid reminder day value");
            }
            
            // Default values
            document.setDocumentType("Svjedodžba");
            document.setStatus("Aktivno");
            document.setArhiva(false);
            document.setDepartment("Glavna Sestra");
            document.setResourceType("Radnik");
            document.setServiceProvider("");
            
            DocumentFile savedDocument = documentRepository.save(document);
            System.out.println("Successfully saved certificate with ID: " + savedDocument.getId());
            
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            System.err.println("Error saving certificate: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/form";
        }
    }

    @PostMapping("/saveTechnicalCheck")
    public String saveTechnicalCheck(@ModelAttribute TechnicalCheckDTO technicalCheckDTO, RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(technicalCheckDTO.getVehicleTechnicalInspectionName());
            document.setNumber(technicalCheckDTO.getRegistrationTechnicalInspection());
            document.setRenewalDate(technicalCheckDTO.getRenewalDateTechnicalInspection());
            document.setServiceProvider(technicalCheckDTO.getServiceTechnicalInspection());
            document.setLocation(technicalCheckDTO.getOutpostTechnicalInspection());
            document.setRenewalPeriod(Integer.parseInt(technicalCheckDTO.getReminderDayVehicleInspection()));
            // Default value for this document type
            document.setDocumentType("Tehnički pregled vozila");
            document.setStatus("Aktivno");
            document.setArhiva(false);
            document.setDepartment("Vozni Park");
            document.setResourceType("Vozilo");

            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/form";
        }
    }

    @PostMapping("/vehicleInsurance")
    public String saveVehicleInsurance(@ModelAttribute VehicleInsuranceDTO insuranceDTO, RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(insuranceDTO.getVehicleInsuranceName());
            document.setNumber(insuranceDTO.getVehicleInsuranceRegistration());
            document.setRenewalDate(insuranceDTO.getRenewalDateVehicleInsurance());
            document.setServiceProvider(insuranceDTO.getServiceVehicleInsurance());
            document.setLocation(insuranceDTO.getVehicleInsuranceOutpost());
            document.setRenewalPeriod(Integer.parseInt(insuranceDTO.getReminderDayVehicleInsurance()));
            // Default value for this document type
            document.setDocumentType("Osiguranje vozila");
            document.setStatus("Aktivno");
            document.setArhiva(false);
            document.setDepartment("Vozni Park");
            document.setResourceType("Vozilo");
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/form";
        }
    }

    @PostMapping("/saveEmploymentcontract")
    public String saveEmploymentContract(@ModelAttribute EmploymentContractDTO contractDTO, RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(contractDTO.getEmploymentContractName() + " " + contractDTO.getEmploymentContractSurname());
            document.setNumber(contractDTO.getIdentificationNumberEmploymentcontract());
            document.setRenewalDate(contractDTO.getRenewalDateEmploymentContract());
            document.setLocation(contractDTO.getOutpostEmploymentContract());
            document.setDepartment(contractDTO.getEmploymentContractDepartment());
            document.setRenewalPeriod(Integer.parseInt(contractDTO.getReminderDayEmploymentContract()));
            // Default value for this document type
            document.setDocumentType("Ugovor o radu");
            document.setStatus("Aktivno");
            document.setArhiva(false);
            document.setResourceType("Radnik");
            document.setServiceProvider("");
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/form";
        }
    }
}
