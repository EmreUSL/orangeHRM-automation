package com.orangehrm.base;

import com.orangehrm.core.ConfigureBrowser;
import com.orangehrm.utilities.ConfigProperties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseTest {

    protected WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    private void configureBrowser() {
        //Implicit Wait
        int implicitWait = Integer.parseInt(ConfigProperties.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize the browser
        driver.manage().window().maximize();

        //Navigate to URL
        try {
            driver.get(ConfigProperties.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Failed to Navigate to the URL: "+ e.getMessage());
        }
    }

    private void launchBrowser() {
        //Initialize the WebDriver base on browser defined in config.properties file
        String browser = ConfigProperties.getProperty("browser");
        driver = ConfigureBrowser.setDriver(browser);
    }

    @BeforeMethod
    public void setUp() throws IOException {
        System.out.println("Setting up Browser: " + this.getClass().getSimpleName());
        launchBrowser();
        configureBrowser();
    }

    @AfterMethod
    public void tearDown() {
        if (driver!=null) {
            try {
                driver.quit();
                System.out.println("Driver Closed successfully");
            } catch (Exception e) {
                System.out.println("Failed to quit driver: " + e.getMessage());
            }
        }
    }

    //Static wait for pause, use whenever you want
    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
