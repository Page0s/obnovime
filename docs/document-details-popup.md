# Dokumentacija: Pop-up Prikaz Detalja Dokumenta

## Sadržaj
1. [Uvod](#uvod)
2. [Arhitektura](#arhitektura)
3. [Komponente](#komponente)
4. [Tok Podataka](#tok-podataka)
5. [Implementacija](#implementacija)
6. [Primjeri Korištenja](#primjeri-korištenja)
7. [Rješavanje Problema](#rješavanje-problema)

## Uvod

Pop-up prikaz detalja dokumenta je funkcionalnost koja omogućuje korisnicima da brzo pregledaju sve važne informacije o dokumentu bez napuštanja glavne stranice. Ova funkcionalnost je implementirana koristeći kombinaciju Spring Boot kontrolera, Thymeleaf fragmenata i Bootstrap modala.

### Ključne Funkcionalnosti
- Prikaz osnovnih informacija o dokumentu
- Izračun dana do početka i kraja obnove
- Prikaz napomena
- Responzivni dizajn
- Zatvaranje klikom izvan pop-upa

## Arhitektura

Sustav koristi MVC (Model-View-Controller) arhitekturu:

```
[Korisnik] -> [DocumentMainForm.html] -> [JavaScript] -> [Controller] -> [Fragment] -> [Prikaz]
```

### Tehnologije
- Backend: Spring Boot
- Frontend: Thymeleaf, Bootstrap 5
- JavaScript: Vanilla JS + Bootstrap Modal API
- Stilovi: Bootstrap CSS + Custom CSS

## Komponente

### 1. Controller (DocumentController.java)
```java
@GetMapping("/documents/{id}/details")
public String showDocumentDetails(@PathVariable Long id, Model model) {
    DocumentFile document = documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Document not found"));
    
    // Izračun dana do obnove
    LocalDate today = LocalDate.now();
    LocalDate renewalDate = document.getRenewalDate();
    int renewalPeriod = Optional.ofNullable(document.getDocumentType().getRenewalPeriod())
            .orElse(0);
    
    LocalDate startDate = renewalDate.minusDays(renewalPeriod);
    
    long daysToStart = ChronoUnit.DAYS.between(today, startDate);
    long daysToEnd = ChronoUnit.DAYS.between(today, renewalDate);
    
    model.addAttribute("document", document);
    model.addAttribute("daysToStart", daysToStart);
    model.addAttribute("daysToEnd", daysToEnd);
    
    return "fragments/document-details :: documentDetails";
}
```

Kontroler je odgovoran za:
- Dohvaćanje dokumenta iz baze podataka
- Izračun dana do početka i kraja obnove
- Pripremu podataka za prikaz
- Vraćanje odgovarajućeg Thymeleaf fragmenta

### 2. Fragment (document-details.html)
Fragment je modularni dio HTML-a koji sadrži strukturu pop-up prozora. Nalazi se u:
`src/main/resources/templates/fragments/document-details.html`

Ključni dijelovi fragmenta:
```html
<div th:fragment="documentDetails">
    <!-- Header -->
    <div class="modal-header">
        <h3 class="modal-title text-primary">Detalji dokumenta</h3>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
    </div>
    
    <!-- Body -->
    <div class="modal-body">
        <!-- Renewal Info -->
        <div class="bg-light p-3 rounded mb-4">
            <div class="row">
                <div class="col-md-6">
                    <strong>Dani do pokretanja obnove:</strong>
                    <span th:text="${daysToStart}">0</span>
                </div>
                <!-- ... -->
            </div>
        </div>
        
        <!-- Document Info Grid -->
        <div class="row g-3 mb-4">
            <!-- Grid items -->
        </div>
        
        <!-- Notes -->
        <div class="bg-light p-3 rounded">
            <div class="fw-bold text-secondary mb-2">Napomena</div>
            <div th:text="${document.notes ?: 'Nema napomene'}"></div>
        </div>
    </div>
</div>
```

### 3. JavaScript (scripts.js)
JavaScript kod koji upravlja prikazom modala:
```javascript
function showDocumentDetails(documentId) {
    const modal = new bootstrap.Modal(document.getElementById('documentDetailsModal'));
    const modalContent = document.querySelector('#documentDetailsModal .modal-content');
    
    // Prikaži loading spinner
    modalContent.innerHTML = '<div class="text-center p-5"><div class="spinner-border"></div></div>';
    
    // Dohvati sadržaj fragmenta
    fetch(`/documents/${documentId}/details`)
        .then(response => response.text())
        .then(html => {
            modalContent.innerHTML = html;
            modal.show();
        })
        .catch(error => {
            modalContent.innerHTML = '<div class="modal-body text-danger">Greška pri učitavanju.</div>';
            modal.show();
        });
}
```

### 4. Modal Container (DocumentMainForm.html)
Container za modal u glavnoj stranici:
```html
<div class="modal fade" id="documentDetailsModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <!-- Fragment će biti učitan ovdje -->
        </div>
    </div>
</div>
```

## Tok Podataka

1. **Inicijacija**:
   - Korisnik klikne na red u tablici
   - Aktivira se `onclick` event s `data-document-id`

2. **JavaScript Obrada**:
   - Poziva se `showDocumentDetails()`
   - Inicijalizira se Bootstrap modal
   - Prikazuje se loading spinner

3. **Backend Obrada**:
   - Controller prima zahtjev s ID-om dokumenta
   - Dohvaća dokument iz baze
   - Izračunava dane do obnove
   - Priprema model za Thymeleaf

4. **Renderiranje**:
   - Thymeleaf renderira fragment
   - JavaScript zamjenjuje sadržaj modala
   - Bootstrap prikazuje modal

## Primjeri Korištenja

### Osnovni Prikaz
1. Otvorite glavnu stranicu s dokumentima
2. Kliknite na bilo koji red u tablici
3. Modal će se pojaviti s detaljima dokumenta

### Zatvaranje Pop-upa
Pop-up se može zatvoriti na tri načina:
1. Klik na X u gornjem desnom kutu
2. Klik izvan modala
3. Pritisak tipke ESC

## Rješavanje Problema

### Česti Problemi

1. **Modal se ne prikazuje**
   - Provjerite konzolu preglednika za JavaScript greške
   - Provjerite je li Bootstrap JS uključen
   - Provjerite jesu li ID-evi modala ispravni

2. **Podaci se ne prikazuju**
   - Provjerite Network tab u Developer Tools
   - Provjerite logove servera
   - Provjerite jesu li nazivi atributa u fragmentu točni

3. **Izračun dana nije točan**
   - Provjerite format datuma u bazi
   - Provjerite vremenske zone
   - Provjerite logiku izračuna u kontroleru

### Debugging Tips

1. **Frontend Debugging**:
   ```javascript
   // Dodajte console.log u showDocumentDetails
   function showDocumentDetails(documentId) {
       console.log('Opening modal for document:', documentId);
       // ...
   }
   ```

2. **Backend Debugging**:
   ```java
   @GetMapping("/documents/{id}/details")
   public String showDocumentDetails(@PathVariable Long id, Model model) {
       log.debug("Fetching details for document: {}", id);
       // ...
   }
   ```

## Najbolje Prakse

1. **Null Checking**
   ```html
   <!-- Uvijek koristite null check za povezane entitete -->
   <div th:text="${document.location != null ? document.location.name : '-'}">
   ```

2. **Error Handling**
   ```javascript
   fetch(`/documents/${documentId}/details`)
       .then(response => {
           if (!response.ok) throw new Error('Network response was not ok');
           return response.text();
       })
       // ...
   ```

3. **Loading States**
   - Uvijek prikazujte loading indicator
   - Jasno komunicirajte greške
   - Omogućite lako zatvaranje modala

## Zaključak

Pop-up prikaz detalja dokumenta je kompleksna funkcionalnost koja kombinira nekoliko tehnologija:
- Spring Boot za backend logiku
- Thymeleaf za renderiranje
- Bootstrap za UI komponente
- JavaScript za interakciju

Ova implementacija omogućuje:
- Brz pregled detalja dokumenta
- Izračun važnih datuma
- Responzivni prikaz
- Dobro korisničko iskustvo

Za dodatna pitanja ili probleme, kontaktirajte tim za podršku ili pogledajte izvorni kod na GitHubu.
