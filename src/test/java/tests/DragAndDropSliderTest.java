package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DragAndDropSliderTest {
	WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @Test
    public void testDragAndDropSlider() {
        // Step 1: Open Selenium Playground
        driver.get("https://www.lambdatest.com/selenium-playground");

        // Click "Drag & Drop Sliders"
        WebElement sliderLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Drag & Drop Sliders"))
        );
        sliderLink.click();

        // Step 2: Select "Default value 15" slider
        WebElement slider = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='15']"))
        );

        WebElement sliderValue = driver.findElement(By.id("rangeSuccess"));

        // Drag until the value is 95
        int targetValue = 95;
        int currentValue = Integer.parseInt(sliderValue.getText());

        while (currentValue < targetValue) {
            actions.dragAndDropBy(slider, 5, 0).perform();
            currentValue = Integer.parseInt(sliderValue.getText());
        }

        // Step 3: Validate the slider value shows 95
        Assert.assertEquals(currentValue, targetValue, "Slider value is not 95!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


