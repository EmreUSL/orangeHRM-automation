package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;
    protected static ActionDriver actionDriver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        actionDriver = new ActionDriver(driver);
    }
}
