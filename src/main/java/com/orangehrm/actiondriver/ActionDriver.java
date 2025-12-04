package com.orangehrm.actiondriver;

import com.orangehrm.core.ConfigureBrowser;
import com.orangehrm.utilities.ConfigProperties;
import com.orangehrm.utilities.LoggerManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;
import java.util.Properties;

/* click
   enterText
   getText
   compareText
   isDisplayed
   scrollToElement
   waitForPageLoad
 */

public class ActionDriver {

     private final WebDriver driver;
     private final WebDriverWait wait;
     private static final Logger logger = LoggerManager.getLogger(ActionDriver.class);

     public ActionDriver(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(ConfigProperties.getProperty("explicitWait"))));
        logger.info("Webdriver instance is created");
     }

     //Method to click an element
     public void click(By locator) {
         try {
             waitForElementClickable(locator);
             driver.findElement(locator).click();
             logger.info("clicked an element");
         } catch (TimeoutException e) {
             logger.error("unable to click element");
         }
     }

     //Method to enter text into an input field
    public void enterText(By locator, String text) {
         try {
             waitForElementVisible(locator);
             driver.findElement(locator).clear();
             driver.findElement(locator).sendKeys(text);
             logger.info("value entered successfully");
         } catch (Exception e) {
             logger.error("Unable to enter the value");
         }
    }

    //Method to get text from an input field
    public String getText(By locator) {
        try {
             waitForElementVisible(locator);
             return driver.findElement(locator).getText();
        } catch (Exception e) {
            logger.error("Unable to get the text");
            return "";
        }
    }

    //Method to compare Two Text
    public boolean compareText(By locator, String text) {
        try {
             waitForElementVisible(locator);
             String actualText = driver.findElement(locator).getText();
             return actualText.equals(text);
        } catch (TimeoutException e) {
            logger.error("Unable to compare the texts");
            return false;
        }
    }

    //Method to check if an element is displayed
    public boolean isDisplayed(By locator) {
         try {
             waitForElementVisible(locator);
             return driver.findElement(locator).isDisplayed();
         } catch (Exception e) {
             System.out.println("Unable to display the element" + e.getMessage());
             return false;
         }
    }

    //Scroll to an element
    public void scrollToElement(By locator) {
         try {
             JavascriptExecutor js = (JavascriptExecutor) driver;
             WebElement element = driver.findElement(locator);
             js.executeScript("arguments[0].scrollIntoView(true);", element);
         } catch (Exception e) {
             logger.error("Unable to scroll the element");
         }
    }

    //Wait for the page load
    public void waitForPageLoad(int timeOutInSeconds) {
         try {
             wait.withTimeout(Duration.ofSeconds(timeOutInSeconds)).until(WebDriver -> Objects.equals(((JavascriptExecutor) driver)
                     .executeScript("return document.readyState"), "complete"));
             logger.info("Page loaded successfully");
         } catch (Exception e) {
             logger.error("Page did not load");
         }
    }

     //Wait for element to be clickable
     private void waitForElementClickable(By locator) {
         try {
             wait.until(ExpectedConditions.elementToBeClickable(locator));
         } catch (TimeoutException e) {
             logger.error("element is not clickable");
         }
     }

     //Wait for element to be visible
     private void waitForElementVisible(By locator) {
         try {
             wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
         } catch (TimeoutException e) {
             logger.error("element is not visible");
         }
     }
}
