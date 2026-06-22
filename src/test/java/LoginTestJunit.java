import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import java.time.Duration;

public class LoginTestJunit {

    WebDriver driver;

    @BeforeEach
    public void setup() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");
        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @RepeatedTest(10)
    public void validlogIn() {

        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.cssSelector("[type='password']")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement flashMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        AssertJUnit.assertTrue(
                flashMessage.getText().contains("You logged into a secure area!")
        );

        driver.findElement(By.className("button")).click();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}