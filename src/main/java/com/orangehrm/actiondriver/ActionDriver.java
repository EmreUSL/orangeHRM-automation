package com.orangehrm.actiondriver;

import com.orangehrm.base.BasePage;
import com.orangehrm.core.ConfigureBrowser;
import com.orangehrm.utilities.ConfigProperties;
import com.orangehrm.utilities.ExtentManager;
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
         String elementDescription = getElementDescription(locator);
         try {
             applyBorder(locator, "green");

             try {
                 waitForElementClickable(locator);
             } catch (TimeoutException e) {
                 logger.warn("Element not clickable, trying normal click anyway...");
             }

             driver.findElement(locator).click();
             logger.info("clicked element --> " + elementDescription);

         } catch (Exception e) {
             applyBorder(locator, "red");
             logger.error("unable to click element");
             ExtentManager.logFailure(driver, "Unable to click element: ", elementDescription);
         }
     }

     //Method to enter text into an input field
    public void enterText(By locator, String text) {
         try {
             applyBorder(locator,"green");
             waitForElementVisible(locator);
             driver.findElement(locator).clear();
             driver.findElement(locator).sendKeys(text);
         } catch (Exception e) {
             applyBorder(locator,"red");
             logger.error("Unable to enter the value");
         }
    }

    //Method to get text from an input field
    public String getText(By locator) {
        try {
            applyBorder(locator,"green");
             waitForElementVisible(locator);
             return driver.findElement(locator).getText();
        } catch (Exception e) {
            applyBorder(locator,"red");
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
            return false;
        }
    }

    //Method to check if an element is displayed
    public boolean isDisplayed(By locator) {
         try {
             applyBorder(locator,"green");
             waitForElementVisible(locator);
             return driver.findElement(locator).isDisplayed();
         } catch (Exception e) {
             applyBorder(locator,"red");
             System.out.println("Unable to display the element" + e.getMessage());
             return false;
         }
    }

    //Scroll to an element
    public void scrollToElement(By locator) {
         try {
             applyBorder(locator,"green");
             JavascriptExecutor js = (JavascriptExecutor) driver;
             WebElement element = driver.findElement(locator);
             js.executeScript("arguments[0].scrollIntoView(true);", element);
         } catch (Exception e) {
             applyBorder(locator,"red");
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

     //Method to get the description of an element using By locator
     public String getElementDescription(By locator) {
         if (driver == null) return "driver is null";
         if (locator == null) return "locator is null";

         try {
         WebElement element = driver.findElement(locator);
         String name = element.getDomAttribute("name");
         String id = element.getDomAttribute("id");
         String text = element.getText();
         String className = element.getDomAttribute("class");
         String placeholder = element.getDomAttribute("placeholder");

         if(isNotEmpty(name)) {return "Element with name" + name; }
         else if (isNotEmpty(id)) {return "Element with id " + id; }
         else if (isNotEmpty(text)) {return "Element with text " + text; }
         else if (isNotEmpty(className)) {return "Element with class " + className; }
         else if (isNotEmpty(placeholder)) {return "Element with placeholder " + placeholder; }
         } catch (Exception e) {
             logger.error("Unable to find element");
         }
         return null;
     }

     private boolean isNotEmpty(String value) {
         return value != null && !value.isEmpty();
     }

     //Utility Method to Border an element
     public void applyBorder(By locator, String color) {
         try {
             WebElement element = driver.findElement(locator);
             String script = "arguments[0].style.border='3px solid " + color + "'";
             JavascriptExecutor js = (JavascriptExecutor) driver;
             js.executeScript(script, element);
             logger.info("border applied successfully");
         } catch (Exception e) {
             logger.warn("Failed to apply border");
         }

     }
}
