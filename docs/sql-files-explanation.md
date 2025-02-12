# Objašnjenje schema.sql i data.sql datoteka

## Uvod
U Spring Boot aplikacijama, `schema.sql` i `data.sql` su posebne SQL datoteke koje se automatski izvršavaju prilikom pokretanja aplikacije. Ove datoteke se nalaze u `src/main/resources` direktoriju i služe za inicijalizaciju baze podataka.

## schema.sql

### Što je schema.sql?
`schema.sql` je SQL datoteka koja definira strukturu baze podataka. Ona sadrži DDL (Data Definition Language) naredbe koje kreiraju tablice, definiraju njihove stupce, primarne i strane ključeve, te ostala ograničenja.

### Kada se izvršava?
- Izvršava se **automatski** pri pokretanju Spring Boot aplikacije
- Izvršava se **prije** `data.sql`
- Izvršava se samo ako je konfigurirana opcija `spring.jpa.hibernate.ddl-auto=none` ili ako se koristi baza podataka koja nije JPA kompatibilna

### Što konkretno radi naš schema.sql?
U našem projektu, `schema.sql` kreira sljedeće tablice:

1. `app_user` - Tablica za korisnike sustava
   - Sadrži informacije o email-u, lozinci i tipu korisnika (CLIENT/ADMIN)
   - Ima automatski generirani ID

2. `document_types` - Tablica za vrste dokumenata
   - Definira različite vrste dokumenata u sustavu (Licenca, Svjedodžba, itd.)
   - Ima jedinstveno ime

3. `vehicle_inspection_periods` - Tablica za periode tehničkog pregleda
   - Definira različite periode inspekcije vozila
   - Sadrži opis i broj dana do obnove
   - Koristi se specifično za dokumente tipa "Periodički pregled vozila"

4. `locations` - Tablica za lokacije
   - Pohranjuje različite lokacije u sustavu
   - Ima jedinstveno ime

5. `resource_types` - Tablica za vrste resursa
   - Definira različite vrste resursa (Oprema, Radnik, Vozilo)
   - Ima jedinstveno ime

6. `document_statuses` - Tablica za statuse dokumenata
   - Definira moguće statuse dokumenata u sustavu
   - Trenutno podržava: Aktivno, Vrijeme za obnovu, Nema obnove, Obnova u tijeku

7. `document_files` - Glavna tablica za dokumente
   - Povezuje sve ostale tablice kroz strane ključeve
   - Sadrži detalje o dokumentima kao što su ime, broj, datum obnove
   - Sadrži reference na:
     * Vrstu dokumenta (document_type_id)
     * Lokaciju (location_id)
     * Vrstu resursa (resource_type_id)
     * Status dokumenta (status_id)
     * Period pregleda vozila (vehicle_inspection_period_id) - samo za vozila

## data.sql

### Što je data.sql?
`data.sql` je SQL datoteka koja služi za inicijalno punjenje baze podataka s početnim (seed) podacima. Ona sadrži DML (Data Manipulation Language) naredbe za unos podataka u tablice.

### Kada se izvršava?
- Izvršava se **automatski** nakon `schema.sql`
- Izvršava se samo jednom pri inicijalnom pokretanju aplikacije
- Neće se izvršiti ako podaci već postoje u tablicama (zbog UNIQUE ograničenja)

### Što konkretno radi naš data.sql?
U našem projektu, `data.sql` unosi sljedeće početne podatke:

1. **Korisnike sustava** (app_user)
   ```sql
   -- Tri osnovna korisnička računa
   INSERT INTO app_user (email, password, user_type) VALUES
   ('glavna.sestra@gmail.com', 'gs123', 'CLIENT'),
   ('kadrovska@gmail.com', 'ka123', 'CLIENT'),
   ('vozni.park@gmail.com', 'vp123', 'CLIENT');
   ```

2. **Lokacije** (locations)
   ```sql
   -- 8 lokacija Zavoda
   INSERT INTO locations (name) VALUES
   ('Krapina'), ('Donja Stubica'), ('Zabok'), ('Zlatar'),
   ('Klanjec'), ('Konjščina'), ('Marija Bistrica'), ('Pregrada');
   ```

3. **Periode tehničkog pregleda vozila** (vehicle_inspection_periods)
   ```sql
   -- Tri perioda ovisno o starosti vozila
   INSERT INTO vehicle_inspection_periods (description, days_until_renewal) VALUES
   ('Jednom godišnje za vozila mlađa od 6 godina', 365),
   ('Svakih 6 mjeseci za vozila starosti 6-10 godina', 182),
   ('Svakih 3 mjeseca za vozila starija od 10 godina', 90);
   ```

4. **Statuse dokumenata** (document_statuses)
   ```sql
   -- Četiri moguća statusa dokumenta
   INSERT INTO document_statuses (name) VALUES
   ('Aktivno'), ('Vrijeme za obnovu'), ('Nema obnove'), ('Obnova u tijeku');
   ```

5. **Vrste resursa** (resource_types)
   ```sql
   -- Tri vrste resursa
   INSERT INTO resource_types (name) VALUES
   ('Oprema'), ('Radnik'), ('Vozilo');
   ```

6. **Vrste dokumenata** (document_types)
   ```sql
   -- Šest vrsta dokumenata
   INSERT INTO document_types (name) VALUES
   ('Licenca'),
   ('Periodički pregled vozila'),
   ('Svjedodžba'),
   ('Tehnički pregled vozila'),
   ('Osiguranje vozila'),
   ('Ugovor o radu');
   ```

7. **Inicijalni dokumenti** (document_files)
   - 4 licence za medicinsku opremu (EKG aparati, aspirator, defibrilator)
   - 3 svjedodžbe za radnike
   - 2 tehnička pregleda za vozila

## Važne napomene
1. Ove datoteke se izvršavaju **samo** ako su prisutne u `src/main/resources` direktoriju
2. Redoslijed izvršavanja je uvijek: prvo `schema.sql`, zatim `data.sql`
3. Ako dođe do greške tijekom izvršavanja bilo koje od ovih datoteka, aplikacija neće uspješno startati
4. Preporučljivo je koristiti `IF NOT EXISTS` u `schema.sql` kako bi se izbjeglo dupliciranje tablica
5. U `data.sql` je dobro koristiti `INSERT IGNORE` ili `ON DUPLICATE KEY UPDATE` ako želimo izbjeći greške kod ponovnog pokretanja

## Konfiguracija
U `application.properties` datoteci imamo sljedeće postavke:

```properties
spring.sql.init.mode=always           # always, embedded, never
spring.jpa.defer-datasource-initialization=true
spring.jpa.hibernate.ddl-auto=none    # none, update, create, create-drop
```

---
*Dokument ažuriran: 9. veljače 2025.*
