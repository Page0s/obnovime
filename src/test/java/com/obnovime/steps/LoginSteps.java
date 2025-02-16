package com.obnovime.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        // Postavljanje ChromeDrivera
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Metoda za sporo unošenje teksta, znak po znak (opcionalno)
    public void slowSendKeys(WebElement element, String text, long delayInMillis) {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Given("Korisnik je na login stranici")
    public void korisnik_je_na_login_stranici() {
        driver.get("https://obnovime-develop.up.railway.app/login");
    }

    @When("Korisnik unese {string} kao email")
    public void korisnik_unese_kao_email(String email) {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        // Ako želiš sporo unošenje, koristi slowSendKeys, inače emailField.sendKeys(email);
        slowSendKeys(emailField, email, 200);
    }

    @When("Korisnik unese {string} kao lozinku")
    public void korisnik_unese_kao_lozinku(String lozinka) {
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        slowSendKeys(passwordField, lozinka, 200);
    }

    @When("Korisnik klikne na gumb za prijavu")
    public void korisnik_klikne_na_gumb_za_prijavu() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("Prijava uspješna i korisnik je preusmjeren na glavnu stranicu")
    public void prijava_uspjesna_i_korisnik_je_preusmjeren_na_glavnu_stranicu() {
        wait.until(ExpectedConditions.urlContains("/main"));
        if (!driver.getCurrentUrl().contains("/main")) {
            throw new AssertionError("Nakon prijave, URL ne sadrži '/main'.");
        }
        // Pauza od 5 sekundi da se vidi main.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("Prikazuje se poruka {string}")
    public void prikazuje_se_poruka(String expectedMessage) {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger")));
        if (!errorMessage.getText().contains(expectedMessage)) {
            throw new AssertionError("Očekivana poruka: " + expectedMessage + " nije prikazana!");
        }
    }
}
