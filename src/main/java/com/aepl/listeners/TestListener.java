package com.aepl.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aepl.reports.ExtentManager;
import com.aepl.reports.ExtentTestManager;
import com.aepl.util.CommonMethod;
import com.aventstack.extentreports.Status;

/**
 * TestListener class implements the ITestListener interface to handle events during
 * the test lifecycle in TestNG. This includes actions on test start, success, failure, 
 * and skip events, as well as setup and teardown operations for the test suite.
 *
 * <p>
 * Key Responsibilities:
 * <ul>
 *   <li>Logs test statuses (PASS, FAIL, SKIP) to the Extent Report.</li>
 *   <li>Captures screenshots for failed and skipped tests to aid debugging.</li>
 *   <li>Initializes and flushes Extent Report at the start and end of the test suite.</li>
 * </ul>
 *
 * <p>
 * Usage:
 * <ul>
 *   <li>Add this class as a listener in the TestNG configuration file (testng.xml).</li>
 *   <li>Ensure the required utility classes (ExtentManager, ExtentTestManager, and CommonMethod) are properly configured.</li>
 * </ul>
 */
public class TestListener implements ITestListener {

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

