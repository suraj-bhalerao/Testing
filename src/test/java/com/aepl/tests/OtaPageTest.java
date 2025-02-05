package com.aepl.tests;

import org.apache.logging.log4j.Level;
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
		excelUtility.initializeExcel("Ota_Page_Test");
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
		String testCaseName = "Checking the buttons on the OTA page";
		String expectedResult = "All buttons should be clicked successfully";
		String actualResult = "";
		String result = "Fail";

		logger.info("Executing test case: {}", testCaseName);

		try {
			otaPage.checkButtons();
			actualResult = "All buttons clicked successfully";
			result = "Pass";
		} catch (Exception e) {
			actualResult = "Exception occurred: " + e.getMessage();
			commonMethod.captureScreenshot(testCaseName);
			logger.error("Error while checking buttons on the OTA page: ", e);
		} finally {
			logger.info("Writing test result to Excel for test case {} ", testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
	}

	@Test(priority = 5)
	public void testCheckActionButtons() {
		String testCaseName = "Testing the action buttons";
		String expectedResult = "Action buttons should work correctly";
		String actualResult = "";
		String result = "Fail";
		logger.info("Executing test case: {}", testCaseName);

		try {
			otaPage.checkActionButtons();
			actualResult = "Action buttons worked as expected";
			result = "Pass";
		} catch (Exception e) {
			actualResult = "Exception occurred: " + e.getMessage();
			commonMethod.captureScreenshot(testCaseName);
			logger.error("Error during action buttons test: ", e);
		} finally {
			logger.info("Writing test results to Excel for {}", testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}

	}

	@Test(priority = 6)
	public void testCheckSearchBoxAndTable() {
		String testCaseName = "Testing search box and table headers";
		String expectedResult = "Search box and the table is validate successfully";
		String actualResult = "";
		String result = "";
		boolean isChecked = false;

		try {
			isChecked = otaPage.checkSearchBoxAndTable();
			actualResult = "Search box and the table is validate successfully";
			result = isChecked ? "PASS" : "FAIL";
		} catch (Exception e) {
			actualResult = "Issue in the search box and the table heading";
			result = isChecked ? "PASS" : "FAIL";
			commonMethod.captureScreenshot(testCaseName);
			logger.log(Level.INFO, "Facing issue in the search box and table heading of the {} ", testCaseName);
			e.getMessage();
		} finally {
			logger.log(Level.INFO, "Writing data to the excel file for testcase {}", testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
	}

	@Test(priority = 7)
	public void testCheckPagination() {
		String testCaseName = "Testing the pagination";
		String expectedResult = "Pagination should work correctly by navigating through pages";
		String actualResult = "";
		String result = "Fail";
		logger.info("Executing test case: {}", testCaseName);

		try {
			otaPage.checkPagination();
			actualResult = "Pagination worked as expected";
			result = "Pass";
		} catch (Exception e) {
			actualResult = "Exception occurred: " + e.getMessage();
			commonMethod.captureScreenshot(testCaseName);
			logger.error("Error during pagination test: ", e);
		} finally {
			logger.info("Writing test results to Excel for {}", testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
	}

	@Test(priority = 8)
	public void testclickOtaBatchReport() {
		String testCaseName = "Testing the OTA Batch Report button";
		String expectedResult = "OTA Batch Report button should be clicked successfully";
		String actualResult = "";
		String result = "Fail";
		logger.info("Executing test case: {}", testCaseName);

		try {

			otaPage.clickOtaBatchReport();
			actualResult = "OTA Batch Report button clicked successfully";
			result = "Pass";
		} catch (Exception e) {
			actualResult = "Exception occurred: " + e.getMessage();
			commonMethod.captureScreenshot(testCaseName);
			logger.error("Error during OTA Batch Report button test: ", e);
		} finally {
			logger.info("Writing test results to Excel for {}", testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
	}

	@Test(priority = 9)
	public void testGetOtaBatchDateWise() {
		String testCaseName = "Testing the OTA Batch Date Wise button";
		String expectedResult = "OTA Batch Date Wise button should be clicked successfully";
		String actualResult = "";
		String result = "Fail";
		logger.info("Executing test case: {}", testCaseName);

		try {
			otaPage.getOtaBatchDateWise();
			actualResult = "OTA Batch Date Wise button clicked successfully";
			result = "Pass";
		} catch (Exception e) {
			actualResult = "Exception occurred: " + e.getMessage();
			commonMethod.captureScreenshot(testCaseName);
			logger.error("Error during OTA Batch Date Wise button test: ", e);
		} finally {
			logger.info("Writing test results to Excel for {}", testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
	}

	@Test(priority = 10)
	public void testChecktableHeading() {
		String testCaseName = "Testing table headings";
		String expectedResult = "Table headings should match the expected values";
		String actualResult = "";
		String result = "Fail";
		logger.info("Executing test case: {}", testCaseName);

		try {
			otaPage.checktableHeading();
			actualResult = "Table headings matched the expected values";
			result = "Pass";
		} catch (Exception e) {
			actualResult = "Exception occurred: " + e.getMessage();
			commonMethod.captureScreenshot(testCaseName);
			logger.error("Error during table headings test: ", e);
		} finally {
			logger.info("Writing test results to Excel for {}", testCaseName);
			excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
		}
	}
	
	@Test(priority= 11)
	public void testCheckReportsButtons() {
		boolean isChecked = otaPage.checkReportsButtons();
		
		if(!isChecked) {
			System.err.println("Error in checking report buttons");
		}else {
			System.out.println("Checked the report buttons");
		}
	}
	
	// added new changes 
}
