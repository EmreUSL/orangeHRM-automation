package com.orangehrm.test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ConfigProperties;
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
        String username = ConfigProperties.getProperty("username");
        String password = ConfigProperties.getProperty("password");
        loginPage.login(username, password);
        Assert.assertTrue(homePage.verifyHomePageIsOpened());
    }



}
