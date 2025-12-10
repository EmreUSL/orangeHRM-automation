package com.orangehrm.test;

import com.orangehrm.utilities.ApiUtility;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest {

    public void verifyGetUserAPI() {

        //Step1 : Define API Endpoint
        String endPoint = "https://jsonplaceholder.typicode.com/users/1";
        ExtentManager.logStep("Verify Get User API");

        //Step2 : Send GET Request
        ExtentManager.logStep("Sending GET Request to the API");
        Response response = ApiUtility.sendGetRequest(endPoint);

        //Step3 : Validate status code
        ExtentManager.logStep("Validating API Response status code");
        boolean isStatusCodeValid = ApiUtility.validateStatusCode(response, 200);

        if(isStatusCodeValid) {
            ExtentManager.logStepValidationForAPI("Status code validation passed");
        } else {
            ExtentManager.logFailureAPI("Status code not as expected");
        }
        Assert.assertTrue(isStatusCodeValid, "Status code is not as expected");

        //Step4: Validate username
        ExtentManager.logStep("Validating API Response body");
        String username = ApiUtility.getJSONValue(response, "username");
        boolean isUserNameValid = "Bret".equals(username);

        if(isUserNameValid) {
            ExtentManager.logStepValidationForAPI("Status code validation passed");
        } else {
            ExtentManager.logFailureAPI("Status code not as expected");
        }
        Assert.assertTrue(isUserNameValid, "Username is not as expected");

    }




}
