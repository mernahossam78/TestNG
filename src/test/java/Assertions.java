import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Assertions {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver and navigate to the login page
    driver = new EdgeDriver();
    driver.manage().window().maximize();
    driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void invalidLogin(){
        /*
        1-enter username
        2-enter password
        3-click login button
        4-verify error message is displayed
         */
        driver.findElement(By.id("user-name")).sendKeys("Merna");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String actualText = driver.findElement(By.tagName("h3")).getText();
        //we will try here a hard assertion by failing one of the asserts, the first 1
        Assert.assertEquals(actualText, "Epic sadface: Username and password do not match any user in this service","error message is displayed");
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo"),"url should contain saucedemo");
    }

    @Test
    public void invalidLoginSoft(){
        SoftAssert softAssert = new SoftAssert();
        /*
        1-enter username
        2-enter password
        3-click login button
        4-verify error message is displayed
         */
        driver.findElement(By.id("user-name")).sendKeys("Merna");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        String actualText = driver.findElement(By.tagName("h3")).getText();
        //we will try here a hard assertion by failing one of the asserts, the first 1
        softAssert.assertEquals(actualText, "Epic sadface: Username  do not match any user in this service","error message is displayed");
        softAssert.assertTrue(driver.getCurrentUrl().contains("saucedemo"),"url should contain saucedemo");
        System.out.println("Second assertion executed");
        //without it will make the test pass even if the first assertion fails, but with it will make the test fail if any of the assertions fail
        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
