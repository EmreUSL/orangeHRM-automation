package com.orangehrm.listeners;

import com.orangehrm.base.BasePage;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener implements ITestListener, IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

    //Triggered when a suite start
    @Override
    public void onStart(ITestContext context) {
        //Init extent reports
        ExtentManager.getReport();
    }

    //Triggered when a suite ends
    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.endTest();
    }

    //Triggered when a test starts
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.startTest(testName);
        ExtentManager.logStep("Test started: " + testName);
    }

    //Triggered when a test success
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if(!result.getTestClass().getName().toLowerCase().contains("api")) {
            ExtentManager.logStepWithScreenshot(BasePage.getDriver(), "Test Passed Succesfully ", "Test End : " + testName + "✅ Test Passed");
        } else {
            ExtentManager.logStepValidationForAPI("Test End : " + testName + "✅ Test Passed");
        }

    }

    //Triggered when a test fails
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String failure = result.getThrowable().getMessage();
        ExtentManager.logStep(failure);

        if(!result.getTestClass().getName().toLowerCase().contains("api")) {
            ExtentManager.logFailure(BasePage.getDriver(),"Test failed", "Test End : " + testName + "❌  Test Failed");
        } else {
            ExtentManager.logFailureAPI("Test End : " + testName + "✅ Test Passed");
        }


    }

    //Triggered when a Test skips
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.logSkip("Test Skipped: " + testName);
    }
}
