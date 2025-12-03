package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final ActionDriver actionDriver;

    private final By adminTab = By.xpath("//span[text()='Admin']");
    private final By userIDButton = By.className("oxd-userdropdown-name");
    private final By logoutButton = By.xpath("//a[text()='Logout']");
    private final By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

    public HomePage(WebDriver driver) {
        this.actionDriver = new ActionDriver(driver);
    }

    //Method to verify is Admin tab is visible
    public boolean isAdminTabVisible() {
        return actionDriver.isDisplayed(adminTab);
    }

    public boolean verifyHomePageIsOpened() {
        return actionDriver.isDisplayed(orangeHRMLogo);
    }

    public void logout() {
        actionDriver.click(userIDButton);
        actionDriver.click(logoutButton);
    }

}
