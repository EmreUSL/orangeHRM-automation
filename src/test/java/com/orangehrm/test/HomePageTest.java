package com.orangehrm.test;


import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ConfigProperties;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test(priority = 1)
    public void verifyHomePageDisplayed() {
        String username = ConfigProperties.getProperty("username");
        String password = ConfigProperties.getProperty("password");
        loginPage.login(username, password);
        Assert.assertTrue(homePage.verifyHomePageIsOpened(), "Home page is not opened");
    }

    @Test(priority = 2)
    public void isAdminTabDisplayed() {
        String username = ConfigProperties.getProperty("username");
        String password = ConfigProperties.getProperty("password");
        loginPage.login(username, password);
        Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab is not visible");
    }

    @Test(priority = 3)
    public void isLogoutSuccess() {
        String username = ConfigProperties.getProperty("username");
        String password = ConfigProperties.getProperty("password");
        loginPage.login(username, password);
        Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab is not visible");
        homePage.logout();
    }


}
