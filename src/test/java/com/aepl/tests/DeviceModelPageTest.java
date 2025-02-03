package com.aepl.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.DeviceModelPage;
import com.aepl.pages.LoginPage;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class DeviceModelPageTest extends TestBase {
	private LoginPage loginPage;
	private DeviceModelPage deviceModel;
	private ExcelUtility excelUtility;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		deviceModel = new DeviceModelPage(driver);
		loginPage = new LoginPage(driver);
		excelUtility = new ExcelUtility();
		excelUtility.initializeExcel("Device Model");
	}

	@Test(priority = 1)
	public void testLogin() {
		loginPage.enterUsername(ConfigProperties.getProperty("valid.username"))
				.enterPassword(ConfigProperties.getProperty("valid.password")).clickLogin();
	}

	@Test(priority = 2)
	public void testClickNavBar() {
		String testCaseName = "Test Navbar Links";
		String expectedURL = ConfigProperties.getProperty("dashboard.url");
		String actualURL = "";
		String result;
		logger.info("Executing the testClickNavBar");
		try {
			logger.info("Trying to click on the nav bar links");
			deviceModel.clickNavBar();
			actualURL = driver.getCurrentUrl();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		} catch (Exception e) {
			logger.warn("Error");
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Successfully clicked on the Device Utility.");
	}

	@Test(priority = 3)
	public void testClickOnDeviceModel() {
		String testCaseName = "Test Device Model Link";
		String expectedURL = ConfigProperties.getProperty("device.model");
		String result = "";

		logger.info("Executing test case: {}", testCaseName);

		try {
			String actualURL = deviceModel.clickDeviceModel();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Test case '{}' completed successfully. Expected URL: {}, Actual URL: {}", testCaseName,
					expectedURL, actualURL);
		} catch (Exception e) {
			logger.error("Error encountered in test case '{}'.", testCaseName, e);
			result = "FAIL";
		} finally {
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL,
					result.equals("PASS") ? expectedURL : "Error occurred", result);
		}
	}

	@Test(priority = 4)
	public void testClickAddDeviceModel() {
		String testCaseName = "Test Add Device Model Button";
		String expectedURL = ConfigProperties.getProperty("add.device.model");
		String result = "";

		logger.info("Executing test case: {}", testCaseName);

		try {
			String actualURL = deviceModel.clickAddDeviceModel();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Test case '{}' completed successfully. Expected URL: {}, Actual URL: {}", testCaseName,
					expectedURL, actualURL);
		} catch (Exception e) {
			logger.error("Error encountered in test case '{}'.", testCaseName, e);
			result = "FAIL";
		} finally {
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL,
					result.equals("PASS") ? expectedURL : "Error occurred", result);
		}
	}

	@Test(priority = 5)
	public void testAddNewDeviceModel() {
		deviceModel.AddNewDeviceModel();
		// from dhananjay branch 
	}
}
