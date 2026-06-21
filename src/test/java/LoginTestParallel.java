import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Assert;
import org.testng.annotations.*;

@Test
public class LoginTestParallel {
    /*here we are running the methods in parallel, so we need to make
    sure that each method has its own driver instance, otherwise we
    will have issues with the tests because they will be sharing the same driver instance
     and they will interfere with each other, so i have a list that contains multiple webdrivers
     */
    ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    WebDriver driver;


    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = ThreadGuard.protect(new EdgeDriver());
        driverThreadLocal.set(driver); //initialize the driver instance for the current thread
        driverThreadLocal.get().manage().window().maximize();
        driverThreadLocal.get().get("https://the-internet.herokuapp.com/login");
    }

    @Test(groups = "loginFun")
    public void validlogIn() {
        driverThreadLocal.get().findElement(By.id("username")).sendKeys("tomsmith");
        driverThreadLocal.get().findElement(By.cssSelector("[type = 'password']")).sendKeys("SuperSecretPassword!");
        driverThreadLocal.get().findElement(By.className("radius")).click();

        Assert.assertTrue(driverThreadLocal.get().findElement(By.id("flash")).isDisplayed(), "error message is displayed");
        // throw new RuntimeException("test failed");
        driverThreadLocal.get().findElement(By.className("button")).click();

    }

    @Test(groups = "loginFun")
    public void inValidlogIn() {
        driverThreadLocal.get().findElement(By.id("username")).sendKeys("merna");
        driverThreadLocal.get().findElement(By.cssSelector("[type = 'password']")).sendKeys("SuperSecretPassword!");
        driverThreadLocal.get().findElement(By.className("radius")).click();
        String actualMessage = driverThreadLocal.get().findElement(By.id("flash")).getText();
        Assert.assertTrue(actualMessage.contains("Your username is invalid!"), "error message is displayed");
        // throw new RuntimeException("test failed");

    }
/*
    @Test(dependsOnMethods = "validlogIn")
    public void logOut() {
        driverThreadLocal.get().findElement(By.className("button")).click();
        String actual = driverThreadLocal.get().findElement(By.id("flash")).getText();
        Assert.assertTrue(actual.contains("You logged out of the secure area!"), "error message is displayed");
    }
*/

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        driverThreadLocal.get().quit();
    }
}
