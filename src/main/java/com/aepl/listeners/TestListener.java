package com.aepl.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aepl.reports.ExtentManager;
import com.aepl.reports.ExtentTestManager;
import com.aepl.util.CommonMethod;
import com.aventstack.extentreports.Status;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        
        // Added the screenshot logic to the listeners so that it can take SS on every test failure
        CommonMethod.captureScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
        
        // Added the screenshot logic to the listeners so that it can take SS on every test failure
        CommonMethod.captureScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.createInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }
}
