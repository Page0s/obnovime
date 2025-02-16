import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Postavljanje ChromeDrivera
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Implicitno čekanje (opcionalno)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Metoda koja simulira sporo unošenje teksta, znak po znak
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

    // Test s točnim podacima – očekuje se preusmjeravanje na glavnu stranicu (npr. URL sadrži '/main')
    @Test
    void testLoginWithValidCredentials() {
        // Otvaranje login stranice
        driver.get("https://obnovime-develop.up.railway.app/login");

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Unos točnih podataka (zamijeni s ispravnim podacima)
        slowSendKeys(emailField, "glavna.sestra@gmail.com", 200);
        slowSendKeys(passwordField, "gs123", 200);

        // Klik na gumb za prijavu
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Eksplicitno čekanje da URL sadrži '/main'
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/main"));

        // Provjera da je preusmjerenje uspješno
        Assertions.assertTrue(driver.getCurrentUrl().contains("/main"),
                "Nakon prijave, URL ne sadrži '/main'.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Test s krivim podacima – očekuje se prikaz poruke o pogrešnim podacima
    @Test
    void testLoginWithInvalidCredentials() {
        // Otvaranje login stranice
        driver.get("https://obnovime-develop.up.railway.app/login");

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Unos krivih podataka
        slowSendKeys(emailField, "neispravan_email@primjer.com", 200);
        slowSendKeys(passwordField, "neispravna_lozinka", 200);

        // Klik na gumb za prijavu
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Eksplicitno čekanje da se pojavi element s porukom o pogrešnim podacima
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger"))
        );

        // Provjera da poruka sadrži očekivani tekst
        Assertions.assertTrue(errorMessage.getText().contains("Neispravni podaci za prijavu"),
                "Poruka o pogrešnim podacima nije prikazana!");
    }
}
