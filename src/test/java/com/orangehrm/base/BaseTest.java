package com.orangehrm.base;

import com.orangehrm.core.ConfigureBrowser;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    protected Properties properties;
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() throws IOException {
        //Load configuration file
        properties = new Properties();
        FileInputStream file = new FileInputStream("src/main/resources/config.properties");
        properties.load(file);

        //Initialize the WebDriver base on browser defined in config.properties file
        String browser = properties.getProperty("browser");
        driver = ConfigureBrowser.setDriver(browser);

        //Implicit Wait
        int implicitWait = Integer.parseInt(properties.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize the browser
        driver.manage().window().maximize();

        //Navigate to URL
        driver.get(properties.getProperty("url"));

    }

    @AfterMethod
    public void tearDown() {
        if (driver!=null) {
            driver.quit();
        }
    }
}
