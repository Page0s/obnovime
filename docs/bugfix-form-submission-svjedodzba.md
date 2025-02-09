# Dokumentacija popravka greške u slanju obrasca

## Opis problema
Prilikom pokušaja slanja obrasca "Svjedodžba" u DocumentEntryForm-u, klik na gumb "Spremi" i potvrda u modalnom dijalogu nisu rezultirali spremanjem podataka. Slanje obrasca nije dosezalo krajnju točku kontrolera `/saveCertificate`.

## Analiza uzroka problema
Problem je pronađen u neispravnoj implementaciji modalnog dijaloga za potvrdu i logici slanja obrasca. Postojala su dva glavna problema:

1. **Konflikt modalnog obrasca**
   - Modalni dijalog za potvrdu sadržavao je vlastiti `<form>` element s drugačijom akcijom
   ```html
   <div class="modal-footer">
       <form action="/main" method="get">  <!-- Ovo je bilo neispravno -->
           <button type="submit" class="btn btn-primary" onclick="submitForm()">Potvrdi</button>
           ...
       </form>
   </div>
   ```
   - Ovaj ugniježđeni obrazac presretao je slanje i preusmjeravao na `/main` bez slanja podataka obrasca

2. **JavaScript implementacija**
   - Funkcija `openConfirmationModal()` je neispravno pohranjivala referencu na obrazac
   - Funkcija `submitForm()` navedena u onclick događaju modalnog gumba nije bila pravilno implementirana
   - Nije postojao pravilan mehanizam za slanje izvornog obrasca nakon potvrde

## Implementacija rješenja

### 1. JavaScript ispravci (scripts.js)
```javascript
function openConfirmationModal(event) {
    event.preventDefault();
    window.formToSubmit = event.target; // Store the form globally
    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    confirmationModal.show();
}

// New function to handle form submission
function confirmAndSubmitForm() {
    if (window.formToSubmit) {
        sessionStorage.setItem('showToast', 'true');
        window.formToSubmit.submit();
    }
}
```

### 2. Ispravci modalnog HTML-a (DocumentEntryForm.html)
```html
<div class="modal-footer">
    <button type="button" class="btn btn-primary" onclick="confirmAndSubmitForm()">Potvrdi</button>
    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Nazad</button>
</div>
```

## Ključne promjene
1. Uklonjen ugniježđeni obrazac iz modalnog dijaloga
2. Implementirano pravilno pohranjivanje reference obrasca koristeći `window.formToSubmit`
3. Stvorena nova funkcija `confirmAndSubmitForm()` za upravljanje stvarnim slanjem obrasca
4. Ažuriran gumb za potvrdu u modalnom dijalogu da poziva ispravnu funkciju za slanje
5. Održana funkcionalnost obavijesti o uspjehu koristeći sessionStorage

## Provjera testiranjem
Nakon implementacije ovih promjena:
1. Slanje obrasca uspješno doseže kontroler na backend-u
2. Podaci se ispravno spremaju u bazu podataka
3. Korisnik se preusmjerava na glavnu stranicu nakon slanja
4. Prikazuje se obavijest o uspjehu

## Tehnički utjecaj
- Poboljšana pouzdanost slanja obrasca
- Eliminirane konfliktne akcije obrazaca
- Održano korisničko iskustvo s modalnim dijalogom za potvrdu
- Očuvana funkcionalnost obavijesti o uspjehu

## Naučene najbolje prakse
1. Izbjegavati ugnježđivanje obrazaca u HTML-u jer može dovesti do neočekivanog ponašanja
2. Pažljivo koristiti globalni window objekt kada je potrebno za privremeno pohranjivanje referenci obrazaca
3. Implementirati pravilno sprječavanje i rukovanje događajima za modalne potvrde
4. Održavati jasno odvajanje između modalnog korisničkog sučelja i logike slanja obrazaca

## Povezane datoteke
- `src/main/resources/templates/DocumentEntryForm.html`
- `src/main/resources/static/scripts.js`
- `src/main/java/com/obnovime/controller/FormController.java`

## Buduća razmatranja
1. Razmotriti implementaciju robusnijeg sustava za rukovanje s više obrazaca
2. Dodati validaciju na strani klijenta prije slanja obrasca
3. Implementirati bolje rukovanje pogreškama i povratne informacije korisnicima
4. Razmotriti korištenje AJAX-a za slanje obrazaca kako bi se izbjegli potpuni osvježi stranice

---
*Dokument kreiran: 5. veljače 2025.*
