package com.orangehrm.test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ConfigProperties;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test
    public void verifyValidLoginTest() {
        ExtentManager.startTest("Valid login test");
        ExtentManager.logStep("Navigating to login page");
        String username = ConfigProperties.getProperty("username");
        String password = ConfigProperties.getProperty("password");
        loginPage.login(username, password);
        ExtentManager.logStep("Verify home page opened or not");
        Assert.assertTrue(homePage.verifyHomePageIsOpened());;
    }

    @Test
    public void invalidLoginTest() {
        String username = ConfigProperties.getProperty("username");;
        loginPage.login(username, "adminnn");
        Assert.assertTrue(loginPage.verifyErrorMessage("Invalid credentials"),"Test Failed: Invalid error message");
    }




}
