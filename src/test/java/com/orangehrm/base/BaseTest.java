package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.core.ConfigureBrowser;
import com.orangehrm.utilities.ConfigProperties;
import com.orangehrm.utilities.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final Logger logger = LogManager.getLogger(BaseTest.class);

    //Configure Browser class içerisinde static olarak tanımlayabilirsin
    public synchronized void configureBrowser() {
        //Implicit Wait
        int implicitWait = Integer.parseInt(ConfigProperties.getProperty("explicitWait"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize the browser
        getDriver().manage().window().maximize();

        //Navigate to URL
        try {
            driver.get().get(ConfigProperties.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Failed to Navigate to the URL: "+ e.getMessage());
        }
    }

    public synchronized void launchBrowser() {
        //Initialize the WebDriver base on browser defined in config.properties file
        String browser = ConfigProperties.getProperty("browser");
        driver.set(ConfigureBrowser.setDriver(browser));
        logger.info("Driver initialized for thread: " + Thread.currentThread().threadId());
    }


    @BeforeMethod
    public synchronized void setUp() throws IOException {
        System.out.println("Setting up Browser: " + this.getClass().getSimpleName());
        launchBrowser();
        configureBrowser();
        logger.info("WebDriver Initialized");
    }

    @AfterMethod
    public synchronized void tearDown() {
        if (driver.get()!=null) {
            try {
                getDriver().quit();
                logger.info("Driver Closed successfully");
            } catch (Exception e) {
                System.out.println("Failed to quit driver: " + e.getMessage());
            }
            driver.remove();
        }
    }

    public WebDriver getDriver() {
        if (driver.get() == null) {
            System.out.println("WebDriver Not Initialized");
            throw new IllegalStateException("WebDriver Not Initialized");
        }
        return driver.get();
    }


    //Static wait for pause, use whenever you want
    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
