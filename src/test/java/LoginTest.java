import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

@Test
public class LoginTest {
    WebDriver driver;


    @BeforeGroups("loginFun")
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @Test(groups = "loginFun")
    public void validlogIn() {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.cssSelector("[type = 'password']")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();

        Assert.assertTrue(driver.findElement(By.id("flash")).isDisplayed(), "error message is displayed");
        // throw new RuntimeException("test failed");
        driver.findElement(By.className("button")).click();

    }

    @Test(groups = "loginFun")
    public void inValidlogIn() {
        driver.findElement(By.id("username")).sendKeys("merna");
        driver.findElement(By.cssSelector("[type = 'password']")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();
        String actualMessage = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(actualMessage.contains("Your username is invalid!"), "error message is displayed");
        // throw new RuntimeException("test failed");

    }

    @Test(dependsOnMethods = "validlogIn")
    public void logOut() {
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(actual.contains("You logged out of the secure area!"), "error message is displayed");
    }


    @AfterGroups(groups = "loginFun")
    public void teardown() {
        driver.quit();
    }
}
