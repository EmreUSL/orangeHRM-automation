package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By adminTab = By.xpath("//span[text()='Admin']");
    private final By userIDButton = By.className("oxd-userdropdown-name");
    private final By logoutButton = By.xpath("//a[text()='Logout']");
    private final By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");
    private final By pimTab = By.xpath("//span[text()='PIM']");

    private final By employeeInformationTab = By.xpath("//span[text()='Employee']");
    private final By employeeSearch = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div/div/div/input");
    private final By searchButton = By.xpath("//button[@type='submit']");

    private final By employeeFirstAndMdleName = By.xpath("//div[@class='oxd-table-card']/div/div[3]");
    private final By employeeLastName = By.xpath("//div[@class='oxd-table-card']/div/div[4]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    //Method to verify is Admin tab is visible
    public boolean isAdminTabVisible() {
        return actionDriver.isDisplayed(adminTab);
    }

    public boolean verifyHomePageIsOpened() {
        return actionDriver.isDisplayed(orangeHRMLogo);
    }

    //Method to Navigate to PIM tab
    public void clickOnPIMTab() {
        actionDriver.click(pimTab);
    }

    public void searchEmployee(String value) {
        //actionDriver.click(employeeInformationTab);
        actionDriver.enterText(employeeSearch, value);
        actionDriver.click(searchButton);
        actionDriver.scrollToElement(employeeFirstAndMdleName);
    }

    //verify employee first and middle name
    public boolean verifyEmployeeFirstAndMdleName(String employeeNameFromDB) {
        return actionDriver.compareText(employeeFirstAndMdleName, employeeNameFromDB);
    }

    public boolean verifyEmployeeLastName(String lastNameFromDB) {
        return actionDriver.compareText(employeeLastName, lastNameFromDB);
    }

    public void logout() {
        actionDriver.click(userIDButton);
        actionDriver.click(logoutButton);
    }

}
