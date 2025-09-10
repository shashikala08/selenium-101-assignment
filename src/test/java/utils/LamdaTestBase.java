package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LamdaTestBase {

    protected WebDriver driver;

    // Method to create driver session on LambdaTest Cloud
    @SuppressWarnings({ "deprecation", "deprecation" })
	public WebDriver createDriver(String browserName, String browserVersion, String platformName, String testName, String buildName) throws Exception {
        // Username & AccessKey from environment variables
        String username = System.getenv("LT_USERNAME");
        String accessKey = System.getenv("LT_ACCESS_KEY");

        if (username == null || accessKey == null) {
            throw new Exception("Please set LT_USERNAME and LT_ACCESS_KEY environment variables.");
        }

        // Browser options (using Chrome as example)
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setBrowserVersion(browserVersion);

        // LambdaTest options
   
        ChromeOptions browserOptions1 = new ChromeOptions();
        browserOptions1.setPlatformName("Windows 10");
        browserOptions1.setBrowserVersion("dev");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "NandipatiS");
        ltOptions.put("accessKey", "LT_cipVEylWi3ls2yUfyFi2pNwDxfRCCy98fRcDDp6WeLThXM1");
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("network", true);
        ltOptions.put("project", "Untitled");
        ltOptions.put("tunnel", true);
        ltOptions.put("console", "true");
        ltOptions.put("networkThrottling", "Regular 4G");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);
        ltOptions.put("accessibility", true);
        browserOptions1.setCapability("LT:Options", ltOptions);

        // Enable debugging features
        ltOptions.put("video", true);
        ltOptions.put("network", true);
        ltOptions.put("console", true);
        ltOptions.put("visual", true);
        ltOptions.put("w3c", true);

        // Attach LT options
        browserOptions1.setCapability("LT:Options", ltOptions);

        // Connect to LambdaTest Hub
        String hub = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";
        driver = new RemoteWebDriver(new URL(hub), browserOptions1);

        // Print Session ID (for submission later)
        SessionId session = ((RemoteWebDriver) driver).getSessionId();
        System.out.println("===== LambdaTest Session ID: " + session.toString());
        System.out.println("Automation logs: https://automation.lambdatest.com/logs/?sessionID=" + session.toString());

        return driver;
    }

    // Mark test status in LT Dashboard
    public void markStatus(boolean passed) {
        if (driver == null) return;
        String status = passed ? "passed" : "failed";
        ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
    }

    // Quit browser
    public void quitDriver() {
        if (driver != null) driver.quit();
    }
}
