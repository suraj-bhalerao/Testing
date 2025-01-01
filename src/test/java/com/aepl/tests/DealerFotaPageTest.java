package com.aepl.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.DealearFotaPage;
import com.aepl.pages.LoginPage;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class DealerFotaPageTest extends TestBase {
	private LoginPage loginPage;
	private ExcelUtility excelUtility;
	private DealearFotaPage dealerFota;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(driver);
		dealerFota = new DealearFotaPage(driver);
		excelUtility = new ExcelUtility();
		excelUtility.initializeExcel("Dealer_FOTA_Test");
	}

	@Test(priority = 1)
	public void login() {
		loginPage.enterUsername(ConfigProperties.getProperty("valid.username"))
				.enterPassword(ConfigProperties.getProperty("valid.password")).clickLogin();
	}

	@Test(priority = 2)
	public void testClickNavBar() {
		logger.info("Executing the 'Dealer FOTA PAGE'");
		String testCaseName = "Clicking on the dealer fota page";
		String expectedResult = ConfigProperties.getProperty("dashboard.url");
		String actualResult = "";
		String result;
		try {
			logger.info("Trying to click on the nav bar links");
			dealerFota.clickNavBar();
			actualResult = driver.getCurrentUrl();
			result = expectedResult.equalsIgnoreCase(actualResult) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		} catch (Exception e) {
			logger.warn("Error");
			e.printStackTrace();
			actualResult = driver.getCurrentUrl();
			captureScreenshot(testCaseName);
			result = expectedResult.equalsIgnoreCase(actualResult) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
		logger.info("Successfully clicked on the 'Dealer FOTA' nav Bar");
	}
}
