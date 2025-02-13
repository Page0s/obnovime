package com.obnovime.controller;

import com.obnovime.model.*;
import com.obnovime.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
public class FormController {
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final LocationRepository locationRepository;
    private final ResourceTypeRepository resourceTypeRepository;
    private final DocumentStatusRepository documentStatusRepository;
    private final VehicleInspectionPeriodRepository vehicleInspectionPeriodRepository;
    private final AppUserRepository userRepository;

    public FormController(
            DocumentRepository documentRepository,
            DocumentTypeRepository documentTypeRepository,
            LocationRepository locationRepository,
            ResourceTypeRepository resourceTypeRepository,
            DocumentStatusRepository documentStatusRepository,
            VehicleInspectionPeriodRepository vehicleInspectionPeriodRepository,
            AppUserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.locationRepository = locationRepository;
        this.resourceTypeRepository = resourceTypeRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.vehicleInspectionPeriodRepository = vehicleInspectionPeriodRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/saveLicense")
    public String saveLicense(
            @RequestParam String name,
            @RequestParam String identificationNumber,
            @RequestParam LocalDate renewalDate,
            @RequestParam String service,
            @RequestParam Long outpostId,
            @RequestParam Long resourceTypeId,
            @RequestParam Integer reminderDay,
            RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(name);
            document.setNumber(identificationNumber);
            document.setRenewalDate(renewalDate);
            document.setServiceProvider(service);
            document.setRenewalPeriod(reminderDay);
            
            // Set document type
            DocumentType licenseType = documentTypeRepository.findByName("Licenca")
                .orElseThrow(() -> new RuntimeException("Document type 'Licenca' not found"));
            document.setDocumentType(licenseType);
            
            // Set location
            Location location = locationRepository.findById(outpostId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
            document.setLocation(location);
            
            // Set resource type
            ResourceType resourceType = resourceTypeRepository.findById(resourceTypeId)
                .orElseThrow(() -> new RuntimeException("Resource type not found"));
            document.setResourceType(resourceType);
            
            // Set status
            DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
            document.setStatus(activeStatus);
            
            // Set created by user
            AppUser user = userRepository.findByEmail("glavna.sestra@gmail.com")
                .orElseThrow(() -> new RuntimeException("Default user not found"));
            document.setCreatedBy(user);
            
            document.setArhiva(false);
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/error";
        }
    }

    @PostMapping("/savePeriodicCheck")
    public String savePeriodicCheck(
            @RequestParam String namePeriodic,
            @RequestParam String registrationPeriodic,
            @RequestParam LocalDate renewalDatePeriodic,
            @RequestParam String servicePeriodic,
            @RequestParam Long outpostId,
            @RequestParam String vehicleAge,
            RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(namePeriodic);
            document.setNumber(registrationPeriodic);
            document.setRenewalDate(renewalDatePeriodic);
            document.setServiceProvider(servicePeriodic);
            
            // Set document type
            DocumentType periodicType = documentTypeRepository.findByName("Periodički pregled vozila")
                .orElseThrow(() -> new RuntimeException("Document type 'Periodički pregled vozila' not found"));
            document.setDocumentType(periodicType);
            
            // Set location
            Location location = locationRepository.findById(outpostId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
            document.setLocation(location);
            
            // Set resource type (always "Vozilo" for periodic checks)
            ResourceType vehicleType = resourceTypeRepository.findByName("Vozilo")
                .orElseThrow(() -> new RuntimeException("Resource type 'Vozilo' not found"));
            document.setResourceType(vehicleType);
            
            // Set vehicle inspection period based on age
            VehicleInspectionPeriod period = vehicleInspectionPeriodRepository.findByDescription(vehicleAge)
                .orElseThrow(() -> new RuntimeException("Vehicle inspection period not found"));
            document.setVehicleInspectionPeriod(period);
            document.setRenewalPeriod(period.getDaysUntilRenewal());
            
            // Set status
            DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
            document.setStatus(activeStatus);
            
            // Set created by user
            AppUser user = userRepository.findByEmail("vozni.park@gmail.com")
                .orElseThrow(() -> new RuntimeException("Default user not found"));
            document.setCreatedBy(user);
            
            document.setArhiva(false);
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/error";
        }
    }

    @PostMapping("/saveCertificate")
    public String saveCertificate(
            @RequestParam String nameCertificate,
            @RequestParam String surnnameCertificate,
            @RequestParam String identificationNumberCertificate,
            @RequestParam LocalDate renewalDateCertificate,
            @RequestParam Long outpostId,
            @RequestParam Integer reminderDayCertificate,
            RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(nameCertificate + " " + surnnameCertificate);
            document.setNumber(identificationNumberCertificate); // This is OIB
            document.setRenewalDate(renewalDateCertificate);
            document.setRenewalPeriod(reminderDayCertificate);
            
            // Set document type
            DocumentType certificateType = documentTypeRepository.findByName("Svjedodžba")
                .orElseThrow(() -> new RuntimeException("Document type 'Svjedodžba' not found"));
            document.setDocumentType(certificateType);
            
            // Set location
            Location location = locationRepository.findById(outpostId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
            document.setLocation(location);
            
            // Set resource type (always "Radnik" for certificates)
            ResourceType workerType = resourceTypeRepository.findByName("Radnik")
                .orElseThrow(() -> new RuntimeException("Resource type 'Radnik' not found"));
            document.setResourceType(workerType);
            
            // Set status
            DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
            document.setStatus(activeStatus);
            
            // Set created by user
            AppUser user = userRepository.findByEmail("glavna.sestra@gmail.com")
                .orElseThrow(() -> new RuntimeException("Default user not found"));
            document.setCreatedBy(user);
            
            document.setArhiva(false);
            document.setServiceProvider(""); // Empty for certificates
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/error";
        }
    }

    @PostMapping("/saveTechnicalCheck")
    public String saveTechnicalCheck(
            @RequestParam String vehicleTechnicalInspectionName,
            @RequestParam String registrationTechnicalInspection,
            @RequestParam LocalDate renewalDateTechnicalInspection,
            @RequestParam String serviceTechnicalInspection,
            @RequestParam Long outpostId,
            @RequestParam Integer reminderDayVehicleInspection,
            RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(vehicleTechnicalInspectionName);
            document.setNumber(registrationTechnicalInspection);
            document.setRenewalDate(renewalDateTechnicalInspection);
            document.setServiceProvider(serviceTechnicalInspection);
            
            // Set location
            Location location = locationRepository.findById(outpostId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
            document.setLocation(location);
            
            document.setRenewalPeriod(reminderDayVehicleInspection);
            
            // Set document type
            DocumentType technicalType = documentTypeRepository.findByName("Tehnički pregled vozila")
                .orElseThrow(() -> new RuntimeException("Document type 'Tehnički pregled vozila' not found"));
            document.setDocumentType(technicalType);
            
            // Set resource type (always "Vozilo" for technical checks)
            ResourceType vehicleType = resourceTypeRepository.findByName("Vozilo")
                .orElseThrow(() -> new RuntimeException("Resource type 'Vozilo' not found"));
            document.setResourceType(vehicleType);
            
            // Set status
            DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
            document.setStatus(activeStatus);
            
            // Set created by user
            AppUser user = userRepository.findByEmail("vozni.park@gmail.com")
                .orElseThrow(() -> new RuntimeException("Default user not found"));
            document.setCreatedBy(user);
            
            document.setArhiva(false);
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/error";
        }
    }

    @PostMapping("/vehicleInsurance")
    public String saveVehicleInsurance(
            @RequestParam String vehicleInsuranceName,
            @RequestParam String vehicleInsuranceRegistration,
            @RequestParam LocalDate renewalDateVehicleInsurance,
            @RequestParam String serviceVehicleInsurance,
            @RequestParam Long outpostId,
            @RequestParam Integer reminderDayVehicleInsurance,
            RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(vehicleInsuranceName);
            document.setNumber(vehicleInsuranceRegistration);
            document.setRenewalDate(renewalDateVehicleInsurance);
            document.setServiceProvider(serviceVehicleInsurance);
            
            // Set location
            Location location = locationRepository.findById(outpostId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
            document.setLocation(location);
            
            document.setRenewalPeriod(reminderDayVehicleInsurance);
            
            // Set document type
            DocumentType insuranceType = documentTypeRepository.findByName("Osiguranje vozila")
                .orElseThrow(() -> new RuntimeException("Document type 'Osiguranje vozila' not found"));
            document.setDocumentType(insuranceType);
            
            // Set status
            DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
            document.setStatus(activeStatus);
            
            // Set created by user
            AppUser user = userRepository.findByEmail("vozni.park@gmail.com")
                .orElseThrow(() -> new RuntimeException("Default user not found"));
            document.setCreatedBy(user);
            
            document.setArhiva(false);
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/error";
        }
    }

    @PostMapping("/saveEmploymentcontract")
    public String saveEmploymentContract(
            @RequestParam String employmentContractName,
            @RequestParam String employmentContractSurname,
            @RequestParam String identificationNumberEmploymentcontract,
            @RequestParam LocalDate renewalDateEmploymentContract,
            @RequestParam Long outpostId,
            @RequestParam Integer reminderDayEmploymentContract,
            RedirectAttributes redirectAttributes) {
        try {
            DocumentFile document = new DocumentFile();
            document.setName(employmentContractName + " " + employmentContractSurname);
            document.setNumber(identificationNumberEmploymentcontract);
            document.setRenewalDate(renewalDateEmploymentContract);
            
            // Set location
            Location location = locationRepository.findById(outpostId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
            document.setLocation(location);
            
            document.setRenewalPeriod(reminderDayEmploymentContract);
            
            // Set document type
            DocumentType contractType = documentTypeRepository.findByName("Ugovor o radu")
                .orElseThrow(() -> new RuntimeException("Document type 'Ugovor o radu' not found"));
            document.setDocumentType(contractType);

            // resouce type
            ResourceType workerType = resourceTypeRepository.findByName("Radnik")
                .orElseThrow(() -> new RuntimeException("Resource type 'Radnik' not found"));
            document.setResourceType(workerType);
            
            // Set status
            DocumentStatus activeStatus = documentStatusRepository.findByName("Aktivno");
            document.setStatus(activeStatus);
            
            // Set created by user
            AppUser user = userRepository.findByEmail("glavna.sestra@gmail.com")
                .orElseThrow(() -> new RuntimeException("Default user not found"));
            document.setCreatedBy(user);
            
            document.setArhiva(false);
            document.setServiceProvider("");
            
            documentRepository.save(document);
            redirectAttributes.addFlashAttribute("showToast", true);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom spremanja: " + e.getMessage());
            return "redirect:/error";
        }
    }
}
