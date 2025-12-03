package com.orangehrm.actiondriver;

import com.orangehrm.core.ConfigureBrowser;
import com.orangehrm.utilities.ConfigProperties;
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

     public ActionDriver(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(ConfigProperties.getProperty("explicitWait"))));
     }

     //Method to click an element
     public void click(By locator) {
         try {
             waitForElementClickable(locator);
             driver.findElement(locator).click();
         } catch (TimeoutException e) {
             System.out.println("unable to click element " + e.getMessage());
         }
     }

     //Method to enter text into an input field
    public void enterText(By locator, String text) {
         try {
             waitForElementVisible(locator);
             driver.findElement(locator).clear();
             driver.findElement(locator).sendKeys(text);
         } catch (Exception e) {
             System.out.println("Unable to enter the value" + e.getMessage());
         }
    }

    //Method to get text from an input field
    public String getText(By locator) {
        try {
             waitForElementVisible(locator);
             return driver.findElement(locator).getText();
        } catch (Exception e) {
            System.out.println("Unable to get the text" + e.getMessage());
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
            System.out.println("Unable to compare the texts" + e.getMessage());
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
             System.out.println("Unable to scroll the element" + e.getMessage());
         }
    }

    //Wait for the page load
    public void waitForPageLoad(int timeOutInSeconds) {
         try {
             wait.withTimeout(Duration.ofSeconds(timeOutInSeconds)).until(WebDriver -> Objects.equals(((JavascriptExecutor) driver)
                     .executeScript("return document.readyState"), "complete"));
             System.out.println("Page loaded successfully");
         } catch (Exception e) {
             System.out.println("Page did not load within"+ timeOutInSeconds + e.getMessage());
         }
    }

     //Wait for element to be clickable
     private void waitForElementClickable(By locator) {
         try {
             wait.until(ExpectedConditions.elementToBeClickable(locator));
         } catch (TimeoutException e) {
             System.out.println("element is not clickable"+ e.getMessage());
         }
     }

     //Wait for element to be visible
     private void waitForElementVisible(By locator) {
         try {
             wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
         } catch (TimeoutException e) {
             System.out.println("element is not visible"+ e.getMessage());
         }
     }
}
