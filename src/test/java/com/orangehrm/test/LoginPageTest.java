package com.orangehrm.test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import org.testng.Assert;
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

    @Test(dataProvider ="validLoginData", dataProviderClass = DataProviders.class, priority = 1)
    public void verifyValidLoginTest(String username, String password) {
        loginPage.login(username, password);
        Assert.assertTrue(homePage.verifyHomePageIsOpened());;
    }

    @Test(dataProvider = "invalidLoginData", dataProviderClass = DataProviders.class, priority = 2)
    public void invalidLoginTest(String username, String password) {
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.verifyErrorMessage("Invalid credentials"),"Test Failed: Invalid error message");
    }




}
