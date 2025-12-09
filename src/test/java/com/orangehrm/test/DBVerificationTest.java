package com.orangehrm.test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ConfigProperties;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class DBVerificationTest extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test(dataProvider = "emplVerification", dataProviderClass = DataProviders.class)
    public void verifyEmployeeNameFromDB(String emplID, String empName) {
        ExtentManager.logStep("Loggin with admin credentials");
        loginPage.login(ConfigProperties.getProperty("username"), ConfigProperties.getProperty("password"));
        ExtentManager.logStep("Click on PIM Tab");
        homePage.clickOnPIMTab();
        ExtentManager.logStep("Search for Employee");
        homePage.searchEmployee(empName);
        ExtentManager.logStep("Get the Employee Name from DB");

        //Fetch the data into a map
        Map<String,String> employeeDetails = DBConnection.getEmployeeDetails(emplID);
        String employefName = employeeDetails.get("firstName");
        String employeedName = employeeDetails.get("middleName");
        String employeelName = employeeDetails.get("lastName");

        String empFMName = (employefName + " " + employeedName).trim();
        ExtentManager.logStep("Verify employee first and middle name");
        Assert.assertTrue(homePage.verifyEmployeeFirstAndMdleName(empFMName));
        ExtentManager.logStep("Verify employee last name");
        Assert.assertTrue(homePage.verifyEmployeeLastName(employeelName));

        ExtentManager.logStep("DB Validation Completed");
    }
}
