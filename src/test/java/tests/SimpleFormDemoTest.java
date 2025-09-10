package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SimpleFormDemoTest {

	WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @Test
    public void testSimpleFormDemo() {
        // Step 1: Open Selenium Playground
        driver.get("https://www.lambdatest.com/selenium-playground");

        // Step 2: Click "Simple Form Demo"
        WebElement simpleFormLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Simple Form Demo"))
        );
        simpleFormLink.click();

        // Step 3: Validate URL contains "simple-form-demo"
        wait.until(ExpectedConditions.urlContains("simple-form-demo"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("simple-form-demo"));

        // Step 4: Create a variable with text
        String message = "Welcome to Lambda Test";

        // Step 5: Enter the text into "Enter Message"
        WebElement messageBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("user-message"))
        );
        messageBox.clear();
        messageBox.sendKeys(message);

        // Step 6: Click "Get Checked Value"
        driver.findElement(By.id("showInput")).click();

        // Step 7: Validate message is displayed
        WebElement outputMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("message"))
        );
        String displayedText = outputMessage.getText();
        Assert.assertEquals(displayedText, message);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

