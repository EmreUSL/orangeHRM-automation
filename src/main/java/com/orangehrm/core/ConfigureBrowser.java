package com.orangehrm.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ConfigureBrowser {

    private static final Logger logger = LogManager.getLogger(ConfigureBrowser.class);

    public static WebDriver setDriver(String browser) {
        return switch (browser) {
            case "chrome" -> {
                logger.info("ChromeDriver Instance is created");
                yield new ChromeDriver();
            }
            case "firefox" -> {
                logger.info("FirefoxDriver Instance is created");
                yield new FirefoxDriver();
            }
            case "edge" -> {
                logger.info("EdgeDriver Instance is created");
                yield new EdgeDriver();
            }
            default -> throw new IllegalArgumentException("Browser not recognized" + browser);
        };
    }
}
