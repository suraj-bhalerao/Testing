package com.aepl.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.DeviceModelPage;
import com.aepl.pages.LoginPage;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class DeviceModelPageTest extends TestBase {
	private DeviceModelPage deviceModel;
	private LoginPage loginPage;
	private WebDriverWait wait;
	private ExcelUtility excelUtility;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		deviceModel = new DeviceModelPage(driver);
		loginPage = new LoginPage(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		excelUtility = new ExcelUtility();
		excelUtility.initializeExcel("Device Model");
	}

	@Test(priority = 1)
	public void navigateToDeviceModelAndClick() {
		String testCaseName = "Navigate to Device Model and Click";
		logger.info("Trying To Log In");

		try {
			loginPage.enterUsername(ConfigProperties.getProperty("valid.username"))
					.enterPassword(ConfigProperties.getProperty("valid.password")).clickLogin();
			logger.info("Login Successful!");

			logger.info("Navigating to Device Model and clicking...");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.cssSelector(".overlay.ng-tns-c14-0.ng-trigger.ng-trigger-fadeIn")));

			List<WebElement> profileDropdownElements = deviceModel
					.waitForVisibilityOfLocators(deviceModel.dropDownList);

			WebElement targetElement = profileDropdownElements.stream()
					.filter(a -> a.getText().equals("Device Utility")).findAny()
					.orElseThrow(() -> new IllegalArgumentException("No element with text 'Device Utility' found"));

			targetElement.click();
			logger.info("Successfully clicked on 'Device Utility'.");
			excelUtility.writeTestDataToExcel(testCaseName, "Expected: Click on 'Device Utility'",
					"Actual: Click successful", "Pass");

		} catch (Exception e) {
			logger.error("Failed to navigate and click on 'Device Utility'", e);
			captureScreenshot(testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, "Expected: Click on 'Device Utility'",
					"Actual: " + e.getMessage(), "Fail");
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

}
