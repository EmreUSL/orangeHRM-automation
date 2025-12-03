package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final ActionDriver actionDriver;

    private final By userNameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.xpath("///*[text()[contains(.,'Invalid credentials')]]");

    public LoginPage(WebDriver driver) {
        this.actionDriver = new ActionDriver(driver);
    }

    //Method to perform login
    public void login(String username, String password) {
        actionDriver.enterText(userNameField, username);
        actionDriver.enterText(passwordField, password);
        actionDriver.click(loginButton);
    }

    //Method to check if error message displayed
    public boolean isErrorMessageDisplayed() {
        return actionDriver.isDisplayed(errorMessage);
    }

    //Method to get the text from Error message
    public String getErrorMessage() {
        return  actionDriver.getText(errorMessage);
    }

    //Verify if error is correct or not
    public boolean verifyErrorMessage(String expectedError) {
        return actionDriver.compareText(errorMessage, expectedError);
    }
}
