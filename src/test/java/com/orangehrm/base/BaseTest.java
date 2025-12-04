package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.core.ConfigureBrowser;
import com.orangehrm.utilities.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseTest {

    protected static WebDriver driver;
    public static final Logger logger = LogManager.getLogger(BaseTest.class);

    //Configure Browser class içerisinde static olarak tanımlayabilirsin
    public void configureBrowser() {
        //Implicit Wait
        int implicitWait = Integer.parseInt(ConfigProperties.getProperty("explicitWait"));
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

    public void launchBrowser() {
        //Initialize the WebDriver base on browser defined in config.properties file
        String browser = ConfigProperties.getProperty("browser");
        driver = ConfigureBrowser.setDriver(browser);
    }

    @BeforeMethod
    public void setUp() throws IOException {
        System.out.println("Setting up Browser: " + this.getClass().getSimpleName());
        launchBrowser();
        configureBrowser();
        logger.info("WebDriver Initialized");
    }

    @AfterMethod
    public void tearDown() {
        if (driver!=null) {
            try {
                driver.quit();
                logger.info("Driver Closed successfully");
            } catch (Exception e) {
                System.out.println("Failed to quit driver: " + e.getMessage());
            }
        }
        driver = null;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            System.out.println("WebDriver Not Initialized");
            throw new IllegalStateException("WebDriver Not Initialized");
        }
        return driver;
    }

    public void setDriver(WebDriver driver) {
        BaseTest.driver = driver;
    }

    //Static wait for pause, use whenever you want
    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
