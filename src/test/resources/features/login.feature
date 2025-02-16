Feature: Login funkcionalnost

  Scenario: Uspješna prijava s točnim podacima
    Given Korisnik je na login stranici
    When Korisnik unese "glavna.sestra@gmail.com" kao email
    And Korisnik unese "gs123" kao lozinku
    And Korisnik klikne na gumb za prijavu
    Then Prijava uspješna i korisnik je preusmjeren na glavnu stranicu

  Scenario: Neuspješna prijava s krivim podacima
    Given Korisnik je na login stranici
    When Korisnik unese "neispravan_email@primjer.com" kao email
    And Korisnik unese "neispravna_lozinka" kao lozinku
    And Korisnik klikne na gumb za prijavu
    Then Prikazuje se poruka "Neispravni podaci za prijavu"
