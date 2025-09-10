package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.LamdaTestBase;
import io.github.bonigarcia.wdm.WebDriverManager;

public class InputFormSubmitTest extends LamdaTestBase {
	
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
	    public void testInputFormSubmit() throws InterruptedException {
	        // Step 1: Open Selenium Playground
	        driver.get("https://www.lambdatest.com/selenium-playground");
	        

	        // Click "Input Form Submit"
	        WebElement formLink = wait.until(
	                ExpectedConditions.elementToBeClickable(By.linkText("Input Form Submit"))
	        );
	        formLink.click();

	        // Step 2: Click Submit without filling anything
	        WebElement submitBtn = wait.until(
	                ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='submit'])[2]"
	                		+ ""))
	        );
	        submitBtn.click();

	        // Step 3: Assert "Please fill out this field." (HTML5 validation message)
	        WebElement nameField = driver.findElement(By.id("name"));
	        String validationMessage = nameField.getAttribute("validationMessage");
	        Assert.assertTrue(validationMessage.contains("Please fill out this field"),
	                "Validation message not shown!");

	        // Step 4 & 5: Fill form fields
	        driver.findElement(By.id("name")).sendKeys("John Doe");
	        driver.findElement(By.id("inputEmail4")).sendKeys("johndoe@example.com");
	        driver.findElement(By.id("inputPassword4")).sendKeys("Password123");

	        driver.findElement(By.id("company")).sendKeys("ABC Corp");
	        driver.findElement(By.id("websitename")).sendKeys("https://example.com");

	        // Country dropdown → select United States
	        Select countryDropdown = new Select(driver.findElement(By.name("country")));
	        countryDropdown.selectByVisibleText("United States");

	        driver.findElement(By.id("inputCity")).sendKeys("New York");
	        driver.findElement(By.id("inputAddress1")).sendKeys("123 Main Street");
	        driver.findElement(By.id("inputAddress2")).sendKeys("Suite 101");
	        driver.findElement(By.id("inputState")).sendKeys("NY");
	        driver.findElement(By.id("inputZip")).sendKeys("10001");

	        // Step 6: Click Submit
	        submitBtn.click();

	        // Step 7: Validate success message
	        WebElement successMsg = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='success-msg hidden'] | //p[@class='success-msg']"))
	        );
	        String actualMsg = successMsg.getText().trim();
	        Assert.assertEquals(actualMsg,
	                "Thanks for contacting us, we will get back to you shortly.",
	                "Success message is incorrect!");
	    }

	    @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}


