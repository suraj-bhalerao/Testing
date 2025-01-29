package com.aepl.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.LoginPage;
import com.aepl.pages.OtaPage;
import com.aepl.util.CommonMethod;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class OtaPageTest extends TestBase {
	private LoginPage loginPage; 
	private ExcelUtility excelUtility;
	private OtaPage otaPage;
	private CommonMethod commonMethod;

	@BeforeClass
	public void setUp() {
		super.setUp();
		this.loginPage = new LoginPage(driver);
		this.otaPage = new OtaPage(driver);
		this.excelUtility = new ExcelUtility();
		this.commonMethod = new CommonMethod(driver);
		excelUtility.initializeExcel("Change_Mobile_Test");
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
		String result = "";

		logger.info("Executing the testClickNavBar");

		try {
			logger.info("Trying to click on the nav bar links");
			otaPage.clickNavBar();
			actualURL = driver.getCurrentUrl();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
		} catch (Exception e) {
			logger.warn("Error while clicking on the nav bar links", e);
			actualURL = driver.getCurrentUrl();
			commonMethod.captureScreenshot(testCaseName);
			result = "FAIL";
		} finally {
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}

		logger.info("Successfully clicked on the Device Utility.");
	}

	@Test(priority = 3)
	public void testClickOnOta() {
		String testCaseName = "Test Ota Link";
		String expectedURL = ConfigProperties.getProperty("ota");
		String actualURL = "";
		String result = "";

		logger.info("Executing test case: {}", testCaseName);

		try {
			actualURL = otaPage.clickDropdown();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Test case '{}' completed successfully. Expected URL: {}, Actual URL: {}", testCaseName,
					expectedURL, actualURL);
		} catch (Exception e) {
			logger.error("Error encountered in test case '{}'.", testCaseName, e);
			commonMethod.captureScreenshot(testCaseName);
			result = "FAIL";
		} finally {
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
	}

	@Test(priority = 4)
	public void testCheckButtons() {
		otaPage.checkButtons();
	}

	@Test(priority = 5)
	public void testCheckSearchBoxAndTable() {
		String batchName = "SB_OTA_TEST";
		List<String> expectedHeaders = Arrays.asList("Batch ID", "Batch Name", "Batch Description", "Created By",
				"Created At", "Batch Breakdown", "Completed Percentage", "Batch Status", "Action");
		
		// Batch ID	Batch Name	Batch Description	Created By	Created At	Batch Breakdown	Completed Percentage	Batch Status	Action

		boolean checkSearchBoxAndTable = otaPage.checkSearchBoxAndTable(batchName, expectedHeaders);
		System.out.println("Checking the search box and table :  ----> " + checkSearchBoxAndTable);
	}
}
