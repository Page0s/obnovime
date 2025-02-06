# Thymeleaf Vodič za ObnoviMe Projekt

Ovaj vodič objašnjava Thymeleaf izraze koji se koriste u ObnoviMe projektu. Služi kao referenca za programere koji rade s našim predlošcima.

## Osnovna Sintaksa

Thymeleaf izrazi su uvijek obuhvaćeni na jedan od ovih načina:
- `th:text="${...}"` - Za tekstualni sadržaj
- `th:attr="${...}"` - Za vrijednosti atributa
- `th:[attribute]="${...}"` - Za specifične HTML atribute

## Najčešće Korišteni Izrazi u Našem Projektu

### 1. Varijabilni Izrazi (`${...}`)

```html
<!-- Pristupanje atributima modela -->
<span th:text="${document.name}">Document Name</span>
```
Koristi se za pristup varijablama proslijeđenim iz kontrolera u pogled.

### 2. Izrazi za Odabir (`*{...}`)

```html
<!-- Koristi se unutar th:object konteksta -->
<div th:object="${document}">
    <span th:text="*{name}">Name</span>
    <span th:text="*{number}">Number</span>
</div>
```
Korisno pri radu s objektima obrazaca.

### 3. URL poveznice (`@{...}`)

```html
<!-- Kreiranje dinamičkih poveznica -->
<a th:href="@{/edit/{id}(id=${document.id})}">Edit</a>
<a th:href="@{/form}">New Document</a>
```
Koristi se za generiranje URL-ova, podržava statičke i dinamičke putanje.

### 4. Uvjetno Prikazivanje

```html
<!-- Jednostavan if uvjet -->
<div th:if="${document.arhiva}">Archived</div>

<!-- If-else uvjet -->
<span th:class="${document.isExpired() ? 'status-expired' : 'status-active'}">Status</span>
```
Koristi se za uvjetno prikazivanje elemenata.

### 5. Iteracija (`th:each`)

```html
<!-- Iteriranje kroz kolekcije -->
<tr th:each="doc : ${documents}">
    <td th:text="${doc.id}"></td>
    <td th:text="${doc.name}"></td>
</tr>
```
Koristi se za prikazivanje lista i kolekcija.

### 6. Rad s Obrascima

```html
<!-- Povezivanje obrazaca -->
<form th:action="@{/save}" th:object="${dokumentFile}" method="post">
    <input type="text" th:field="*{name}" />
    <input type="date" th:field="*{renewalDate}" />
</form>
```
Koristi se za slanje obrazaca i povezivanje podataka.

### 7. Dinamičko Stiliziranje

```html
<!-- Dinamičke CSS klase -->
<tr th:class="${document.getRowColor()}">
    <!-- sadržaj retka -->
</tr>
```
Koristi se za dinamičko stiliziranje temeljeno na uvjetima.

### 8. Spajanje Tekstova

```html
<!-- Kombiniranje statičkog teksta i varijabli -->
<span th:text="'Document: ' + ${document.name}">Document Name</span>
```
Koristi se kada kombiniramo statički tekst s dinamičkim vrijednostima.

## Najbolje Prakse

1. **Zadane Vrijednosti**: Uvijek osigurajte zadani sadržaj unutar HTML oznaka za bolje iskustvo razvoja i rezervni sadržaj:
   ```html
   <span th:text="${document.name}">Primjer Imena Dokumenta</span>
   ```

2. **Validacija Obrazaca**: Koristite `th:errors` za prikazivanje grešaka pri validaciji:
   ```html
   <span th:if="${#fields.hasErrors('name')}" th:errors="*{name}">Greška u Imenu</span>
   ```

3. **Sigurnost**: Koristite `sec:authorize` za prikaz sadržaja temeljenog na ulogama:
   ```html
   <div sec:authorize="hasRole('ADMIN')">Admin Sadržaj</div>
   ```

## Uobičajene Pomoćne Funkcije

### Formatiranje Datuma
```html
<!-- Formatiranje datuma -->
<span th:text="${#temporals.format(document.renewalDate, 'dd.MM.yyyy')}">01.01.2024</span>
```

### Formatiranje Brojeva
```html
<!-- Formatiranje brojeva -->
<span th:text="${#numbers.formatDecimal(document.renewalPeriod, 1, 'POINT', 0, 'COMMA')}">60</span>
```

## Savjeti za Razvoj

1. Koristite debug način rada Thymeleaf predložaka tijekom razvoja:
   ```html
   <html xmlns:th="http://www.thymeleaf.org">
   ```

2. Iskoristite alate za razvoj u pregledniku za pregled generiranog HTML-a i otklanjanje grešaka.

3. Koristite smislena imena varijabli u kontrolerima koja se podudaraju s izrazima u predlošcima.

## Dodatni Resursi

- [Službena Thymeleaf Dokumentacija](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [Spring + Thymeleaf Integracija](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)
