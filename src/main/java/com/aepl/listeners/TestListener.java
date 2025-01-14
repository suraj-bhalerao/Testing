package com.aepl.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aepl.base.TestBase;
import com.aepl.reports.ExtentManager;
import com.aepl.reports.ExtentTestManager;
import com.aepl.util.CommonMethod;
import com.aventstack.extentreports.Status;

public class TestListener extends TestBase implements ITestListener {

    /**
     * Invoked when a test case starts. 
     * Initializes a test entry in the Extent Report.
     *
     * @param result provides details of the test case that is starting.
     */
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.startTest(testName);
        ExtentTestManager.getTest().log(Status.INFO, "Test Started: " + testName);
    }

    /**
     * Invoked when a test case passes successfully.
     * Logs a PASS status to the Extent Report.
     *
     * @param result provides details of the successfully executed test case.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed: " + testName);
    }

    /**
     * Invoked when a test case fails.
     * Logs a FAIL status to the Extent Report and captures a screenshot.
     *
     * @param result provides details of the failed test case, including any throwable exception.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + testName);
        ExtentTestManager.getTest().log(Status.FAIL, "Cause: " + (throwable != null ? throwable.getMessage() : "Unknown"));

        try {
            CommonMethod.captureScreenshot(testName);
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + testName);
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Invoked when a test case is skipped.
     * Logs a SKIP status to the Extent Report and captures a screenshot.
     *
     * @param result provides details of the skipped test case.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped: " + testName);

        try {
        	CommonMethod.captureScreenshot(testName);
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + testName);
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Invoked when the test suite starts.
     * Initializes the Extent Report instance.
     *
     * @param context provides details of the test context.
     */
    @Override
    public void onStart(ITestContext context) {
    	if(context.getName().isEmpty()) {
    		throw new NullPointerException();
    	}
    	
        ExtentManager.createInstance();
//        ExtentTestManager.getTest().log(Status.INFO, "Test Suite Started: " + context.getName());
    }

    /**
     * Invoked when the test suite finishes.
     * Flushes and finalizes the Extent Report.
     *
     * @param context provides details of the test context.
     */
    @Override
    public void onFinish(ITestContext context) {
        ExtentTestManager.getTest().log(Status.INFO, "Test Suite Finished: " + context.getName());
        ExtentManager.flush();
    }
}



























//package com.aepl.listeners;
//
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//import com.aepl.base.TestBase;
//import com.aepl.reports.ExtentManager;
//import com.aepl.reports.ExtentTestManager;
//import com.aepl.util.CommonMethod;
//import com.aventstack.extentreports.Status;
//
///**
// * TestListener class implements ITestListener to handle test lifecycle events
// * like test start, success, failure, skip, and suite start/finish.
// */
//public class TestListener extends TestBase implements ITestListener {
//
//    /**
//     * Invoked when the test suite starts.
//     * Initializes the WebDriver instance and Extent Report.
//     */
//    @Override
//    public void onStart(ITestContext context) {
//        // Initialize Extent Report instance
//        ExtentManager.createInstance();
//
//        // Initialize WebDriver (using the setup from TestBase)
//        if (driver == null) {
//            setUp();  // Call the setUp method from TestBase to initialize WebDriver
//        }
//
//        // Log test suite start information
////        ExtentTestManager.getTest().log(Status.INFO, "Test Suite Started: " + context.getName());
//    }
//
//    /**
//     * Invoked when the test suite finishes.
//     * Flushes the Extent Report and quits the WebDriver.
//     */
//    @Override
//    public void onFinish(ITestContext context) {
//        // Log test suite finish information
//        ExtentTestManager.getTest().log(Status.INFO, "Test Suite Finished: " + context.getName());
//
//        // Flush the Extent Report data
//        ExtentManager.flush();
//
//        // Quit the WebDriver and perform cleanup (using tearDown from TestBase)
//        if (driver != null) {
//            tearDown();  // Call the tearDown method from TestBase to quit the WebDriver
//        }
//    }
//
//    /**
//     * Invoked when a test case starts.
//     * Initializes a test entry in the Extent Report.
//     */
//    @Override
//    public void onTestStart(ITestResult result) {
//        String testName = result.getMethod().getMethodName();
//        ExtentTestManager.startTest(testName);
//        ExtentTestManager.getTest().log(Status.INFO, "Test Started: " + testName);
//    }
//
//    /**
//     * Invoked when a test case passes successfully.
//     * Logs a PASS status to the Extent Report.
//     */
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        String testName = result.getMethod().getMethodName();
//        ExtentTestManager.getTest().log(Status.PASS, "Test Passed: " + testName);
//    }
//
//    /**
//     * Invoked when a test case fails.
//     * Logs a FAIL status to the Extent Report and captures a screenshot.
//     */
//    @Override
//    public void onTestFailure(ITestResult result) {
//        String testName = result.getMethod().getMethodName();
//        Throwable throwable = result.getThrowable();
//
//        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + testName);
//        ExtentTestManager.getTest().log(Status.FAIL, "Cause: " + (throwable != null ? throwable.getMessage() : "Unknown"));
//
//        try {
//            CommonMethod.captureScreenshot(testName);  // Screenshot on failure
//            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + testName);
//        } catch (Exception e) {
//            ExtentTestManager.getTest().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Invoked when a test case is skipped.
//     * Logs a SKIP status to the Extent Report and captures a screenshot.
//     */
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        String testName = result.getMethod().getMethodName();
//        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped: " + testName);
//
//        try {
//            CommonMethod.captureScreenshot(testName);  // Screenshot on skip
//            ExtentTestManager.getTest().log(Status.FAIL, "Test Skipped: " + testName);
//        } catch (Exception e) {
//            ExtentTestManager.getTest().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
//        }
//    }
//}

