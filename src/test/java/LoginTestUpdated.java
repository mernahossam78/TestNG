import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Listeners(CustomListeners.TestNGListeners.class) //that is a method to configure the listeners
@Test
public class LoginTestUpdated {

    WebDriver driver;


    @BeforeMethod(alwaysRun = true)
    public void setup() {
        LogUtils.info("setup method is running");
        driver = ThreadGuard.protect(new EdgeDriver());
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
        LogUtils.info("Page navigated to login page");
    }

    @Test(groups = "loginFun")
    public void validlogIn() {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        LogUtils.info("username entered");
        driver.findElement(By.cssSelector("[type = 'password']")).sendKeys("SuperSecretPassword!");
        LogUtils.info("password entered");
        driver.findElement(By.className("radius")).click();
        LogUtils.info("Login button clicked");
        WebElement flashmsgvalid = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        Assert.assertTrue(flashmsgvalid.getText().contains("You logged into a secure area!"), "error message is displayed");

        // throw new RuntimeException("test failed");
        driver.findElement(By.className("button")).click();

    }

    @Test(groups = "loginFun")
    public void inValidlogIn() {
        driver.findElement(By.id("username")).sendKeys("merna");
        driver.findElement(By.cssSelector("[type = 'password']")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();
        WebElement flashmsg = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        Assert.assertTrue(flashmsg.getText().contains("Your username is invalid!"), "error message is displayed");
        // throw new RuntimeException("test failed");

    }

    /*
        @Test(groups = "loginFun", dependsOnMethods = "validlogIn")
        public void logOut() {
            driver.findElement(By.className("button")).click();

            WebElement flash = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));

            Assert.assertTrue(
                    flash.getText().contains("You logged out of the secure area!"), "error message is displayed");
        }

    */
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        driver.quit();
    }
}
