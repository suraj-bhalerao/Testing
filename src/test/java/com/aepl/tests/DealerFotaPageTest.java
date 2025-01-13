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
		String expectedResult = ConfigProperties.getProperty("dealer.fota");
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
			actualResult = driver.getCurrentUrl();
			result = expectedResult.equalsIgnoreCase(actualResult) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
	}

	@Test(priority = 3)
	public void testclickAddApprovedFileButton() {
		logger.info("Testing the app approved file button is working");
		String testCaseName = "Test Add Approved File Button";
		String expectedResult = ConfigProperties.getProperty("upload.csv");
		String actualResult = "";
		String result = "";

		try {
			dealerFota.clickAddApprovedFileButton();
			actualResult = driver.getCurrentUrl();
			result = expectedResult.equalsIgnoreCase(actualResult) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		} catch (Exception e) {
			logger.warn("Error in the Dealer Fota Page - Add Approved File");
			actualResult = driver.getCurrentUrl();
			result = expectedResult.equalsIgnoreCase(actualResult) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
	}

	@Test(priority = 4)
	public void testAddNewFileAndValidate() {
		logger.info("Testing the add new file feature");

		String testCaseName = "Testing new file added and validating it";
		String expectedToastMessage = "File uploaded successfully";
		String actualMessage = "";
		String result = "";

		try {
			boolean isFileValidated = dealerFota.addNewFileAndValidate(expectedToastMessage);
			System.out.println(isFileValidated);

			if (isFileValidated) {
				result = "Pass";  
				actualMessage = "File uploaded and validated successfully";
				logger.info("Test case passed: " + testCaseName);
			} else {
				result = "Fail";
				actualMessage = "File validation failed";
				logger.error("Test case failed: " + testCaseName);
			}
		} catch (Exception e) {
			result = "Fail";
			actualMessage = e.getMessage();
			logger.error("Exception occurred during test execution: " + testCaseName, e);
		} finally {
			excelUtility.writeTestDataToExcel(testCaseName, expectedToastMessage, actualMessage, result);
		}
	}

}
