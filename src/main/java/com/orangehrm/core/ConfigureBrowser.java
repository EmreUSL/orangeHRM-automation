package com.orangehrm.core;

import com.orangehrm.utilities.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class ConfigureBrowser {

    private static final Logger logger = LogManager.getLogger(ConfigureBrowser.class);

    public static WebDriver setDriver(String browser) {
        return switch (browser) {
            case "chrome" -> {
                WebDriver driver = new ChromeDriver();
                ExtentManager.registerDriver(driver);
                logger.info("ChromeDriver Instance is created");
                yield driver;
            }
            case "firefox" -> {
                WebDriver driver = new FirefoxDriver();
                ExtentManager.registerDriver(driver);
                logger.info("FirefoxDriver Instance is created");
                yield driver;
            }
            case "safari" -> {
                WebDriver driver = new SafariDriver();
                ExtentManager.registerDriver(driver);
                logger.info("SafariDriver Instance is created");
                yield driver;
            }
            default -> throw new IllegalArgumentException("Browser not recognized" + browser);
        };
    }
}
