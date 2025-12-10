package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class BasePage {

    private static final ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
    protected static ActionDriver actionDriver;
    protected static ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);

    public BasePage(WebDriver driver) {
        tdriver.set(driver);
        actionDriver = new ActionDriver(driver);
    }

    public static WebDriver getDriver() {
        return tdriver.get();
    }

    public static SoftAssert getSoftAssert() {
        return softAssert.get();
    }
}
