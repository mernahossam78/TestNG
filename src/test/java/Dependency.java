import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class Dependency {
    WebDriver driver;


    @BeforeClass
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @Test
    public void logIn() {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.cssSelector("[type = 'password']")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();

        Assert.assertTrue(driver.findElement(By.id("flash")).isDisplayed(), "error message is displayed");
        // throw new RuntimeException("test failed");
    }

    @Test(dependsOnMethods = "logIn")
    public void logOut() {
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(actual.contains("You logged out of the secure area!"), "error message is displayed");
    }


    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
