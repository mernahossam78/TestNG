import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Parameters {
    WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }
@org.testng.annotations.Parameters({"username", "password"})
    @Test
    public void validlogIn(String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.cssSelector("[type = 'password']")).sendKeys(password);
        driver.findElement(By.className("radius")).click();

        Assert.assertTrue(driver.findElement(By.id("flash")).isDisplayed(), "error message is displayed");
        // throw new RuntimeException("test failed");
        driver.findElement(By.className("button")).click();

    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        driver.quit();
    }
}

