package com.orangehrm.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final Map<Long, WebDriver> driverMap = new HashMap<>();

    //Init extent report
    public synchronized static ExtentReports getReport() {
        if (extent == null) {
           String reportPath = System.getProperty("user.dir")+"/src/main/resources/extentReport/extentReport.html";
           ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
           spark.config().setReportName("Automation Test Report");
           spark.config().setDocumentTitle("OrangeHRM Report");
           spark.config().setTheme(Theme.DARK);

           extent = new ExtentReports();
           extent.attachReporter(spark);
           extent.setSystemInfo("Operating System", System.getProperty("os.name"));
           extent.setSystemInfo("Java Version", System.getProperty("java.version"));
           extent.setSystemInfo("User Name", System.getProperty("user.name"));
        }

        return extent;
    }

    //Start the Test
    public synchronized static void startTest(String testName) {
        ExtentTest extentTest = getReport().createTest(testName);
        test.set(extentTest);
    }

    //End a Test
    public synchronized static void endTest() {
        getReport().flush();
    }

    //Get Current Thread's test
    public synchronized static ExtentTest getTest() {
        return test.get();
    }

    //Method to get the name of the current test
    public static String getTestName() {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            return currentTest.getModel().getName();
        } else {
            return "No test is currently running";
        }
    }

    //Log a step
    public static void logStep(String message) {
        getTest().info(message);
    }

    //Log a step validation with screenshoot
    public static void logStepWithScreenshot(WebDriver driver, String message, String screenshotMessage) {
        getTest().pass(message);
        attachScreenshot(driver, screenshotMessage);
    }

    //log a failure
    public static void logFailure(WebDriver driver, String message, String screenshotMessage) {
        getTest().fail(message);
        attachScreenshot(driver, screenshotMessage);
    }

    //log a skip
    public static void logSkip(WebDriver driver, String message, String screenshotMessage) {
        getTest().skip(message);
    }

    public synchronized static String takeScreenShot(WebDriver driver, String screenshotName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        //Format date and Time
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        //Saving the screenshot to a file
        String destinationPath = System.getProperty("user.dir")+"/src/main/resources/screenshots/" + "_" + timeStamp + ".png";
        File finalPath = new File(destinationPath);
        try {
            FileUtils.copyFile(src, finalPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return convertToBase64(src);
    }

    //Convert screenshoot to base64 format
    public static String convertToBase64(File screenshot) {
        String base64Format = "";
        //Read the file content into a byte array
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
            base64Format = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return base64Format;
    }

    //Attach screenshot to report using Base64
    public synchronized static void attachScreenshot(WebDriver driver, String message) {
        try {
            String screenshot = takeScreenShot(driver, getTestName());
            getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
        } catch (Exception e) {
            getTest().fail("Failed to attach screenshot:" + message);
            e.printStackTrace();
        }
    }

    //Register WebDriver for current Thread
    public static void registerDriver(WebDriver driver) {
        driverMap.put(Thread.currentThread().threadId(), driver);
    }



}
