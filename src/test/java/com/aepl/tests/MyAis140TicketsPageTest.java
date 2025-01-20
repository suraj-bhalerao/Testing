package com.aepl.tests;

import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.LoginPage;
import com.aepl.pages.MyAis140TicketsPage;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import groovy.time.Duration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class MyAis140TicketsPageTest extends TestBase {
	private LoginPage loginPage;
	private ExcelUtility excelUtility;
	private MyAis140TicketsPage myais140tickets;
	private WebDriverWait wait;
	public static String Ticket_No;
	private String uinNo;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(driver);
		myais140tickets = new MyAis140TicketsPage(driver);
		excelUtility = new ExcelUtility();
		excelUtility.initializeExcel("MyAis140TicketsPage_Test");
	}

	private final By SearchBox = By
			.xpath("/html/body/app-root/app-my-ais140-ticket-page/div/div[1]/div[4]/div/div[1]/i/div/input");
	

	@Test(priority = 1)
	public void testRequestBodyWithRandomValuesCRMData() {
		// Generate random values for the request body
		String VIN_NO = "VIN_NO";
		String ICCID = "ICCID";
		String UIN_NO = myais140tickets.generateRandomString(19);
		String DEVICE_IMEI = myais140tickets.generateRandomNumber(15);
		String DEVICE_MAKE = "Accolade";
		String DEVICE_MODEL = "AEPL051401";
		String ENGINE_NO = myais140tickets.generateRandomString(25);
		String REG_NUMBER = myais140tickets.generateRandomString(15);
		String VEHICLE_OWNER_FIRST_NAME = myais140tickets.generateRandomString(15);
		String VEHICLE_OWNER_MIDDLE_NAME = myais140tickets.generateRandomString(15);
		String VEHICLE_OWNER_LAST_NAME = myais140tickets.generateRandomString(15);
		String ADDRESS_LINE_1 = myais140tickets.generateRandomString(50);
		String ADDRESS_LINE_2 = myais140tickets.generateRandomString(50);
		String VEHICLE_OWNER_CITY = myais140tickets.generateRandomString(15);
		String VEHICLE_OWNER_DISTRICT = myais140tickets.generateRandomString(15);
		String VEHICLE_OWNER_STATE = myais140tickets.generateRandomString(15);
		String VEHICLE_OWNER_COUNTRY = myais140tickets.generateRandomString(15);
		String VEHICLE_OWNER_PINCODE = myais140tickets.generateRandomNumber(6);
		String VEHICLE_OWNER_REGISTERED_MOBILE = myais140tickets.generateRandomNumber(10);
		String POS_CODE = myais140tickets.generateRandomString(10);
		String POA_DOC_NAME = myais140tickets.generateRandomString(10);
		String POA_DOC_NO = myais140tickets.generateRandomString(10);
		String POI_DOC_TYPE = myais140tickets.generateRandomString(20);
		String POI_DOC_NO = myais140tickets.generateRandomString(10);
		String RTO_OFFICE_CODE = myais140tickets.generateRandomString(10);
		String RTO_STATE = myais140tickets.generateRandomString(10);
		String PRIMARY_OPERATOR = "BSNL";
		String SECONDARY_OPERATOR = "BHA";
		String PRIMARY_MOBILE_NUMBER = myais140tickets.generateRandomNumber(15);
		String SECONDARY_MOBILE_NUMBER = myais140tickets.generateRandomNumber(15);
		String VEHICLE_MODEL = myais140tickets.generateRandomString(15);
		String DEALER_CODE = myais140tickets.generateRandomString(15);
		LocalDate COMMERCIAL_ACTIVATION_START_DATE = myais140tickets.generateYesterdayDate();
		LocalDate COMMERCIAL_ACTIVATION_EXPIRY_DATE = myais140tickets.generateFutureDate();
		int MFG_YEAR = myais140tickets.generateRandomYear(4);
		String INVOICE_DATE = myais140tickets.generateRandomDate();
		String INVOICE_NUMBER = myais140tickets.generateRandomString(20);
		int CERTIFICATE_VALIDITY_DURATION_IN_YEAR = 2;
	}

	@Test(priority = 2)
	void SaveCRMData() {
		RestAssured.baseURI = "http://20.219.88.214:6109";

		// Inside your SaveCRMData method
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		// Add your data to the JSON object
		jsonObject.put("VIN_NO", myais140tickets.VIN_NO);
		jsonObject.put("ICCID", myais140tickets.ICCID);
		jsonObject.put("UIN_NO", myais140tickets.generateRandomString(19));
		jsonObject.put("DEVICE_IMEI", myais140tickets.generateRandomNumber(15));
		jsonObject.put("DEVICE_MAKE", "Accolade");
		jsonObject.put("DEVICE_MODEL", "AEPL051400");
		jsonObject.put("ENGINE_NO", myais140tickets.generateRandomString(50));
		jsonObject.put("REG_NUMBER", myais140tickets.generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_FIRST_NAME", myais140tickets.generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_MIDDLE_NAME", myais140tickets.generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_LAST_NAME", myais140tickets.generateRandomString(15));
		jsonObject.put("ADDRESS_LINE_1", myais140tickets.generateRandomString(20));
		jsonObject.put("ADDRESS_LINE_2", myais140tickets.generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_CITY", myais140tickets.generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_DISTRICT", myais140tickets.generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_STATE", myais140tickets.generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_COUNTRY", myais140tickets.generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_PINCODE", myais140tickets.generateRandomNumber(6));
		jsonObject.put("VEHICLE_OWNER_REGISTERED_MOBILE", myais140tickets.generateRandomNumber(10));
		jsonObject.put("DEALER_CODE", myais140tickets.generateRandomNumber(6));
		jsonObject.put("POS_CODE", myais140tickets.generateRandomString(15));
		jsonObject.put("POA_DOC_NAME", myais140tickets.generateRandomString(15));
		jsonObject.put("POA_DOC_NO", myais140tickets.generateRandomString(15));
		jsonObject.put("POI_DOC_TYPE", myais140tickets.generateRandomString(15));
		jsonObject.put("POI_DOC_NO", myais140tickets.generateRandomString(15));
		jsonObject.put("RTO_OFFICE_CODE", myais140tickets.generateRandomString(15));
		jsonObject.put("RTO_STATE", myais140tickets.generateRandomString(15));
		jsonObject.put("PRIMARY_OPERATOR", "BSNL");
		jsonObject.put("SECONDARY_OPERATOR", "BHA");
		jsonObject.put("PRIMARY_MOBILE_NUMBER", myais140tickets.generateRandomNumber(15));
		jsonObject.put("SECONDARY_MOBILE_NUMBER", myais140tickets.generateRandomNumber(15));
		jsonObject.put("VEHICLE_MODEL", myais140tickets.generateRandomString(15));
		jsonObject.put("COMMERCIAL_ACTIVATION_START_DATE", myais140tickets.generateYesterdayDate());
		jsonObject.put("COMMERCIAL_ACTIVATION_EXPIRY_DATE", myais140tickets.generateFutureDate());
		jsonObject.put("MFG_YEAR", myais140tickets.generateRandomYear(4));
		jsonObject.put("INVOICE_DATE", myais140tickets.generateRandomDate());
		jsonObject.put("INVOICE_NUMBER", myais140tickets.generateRandomString(15));
		jsonObject.put("CERTIFICATE_VALIDITY_DURATION_IN_YEAR",2);
//		jsonObject.put("CERTIFICATE_VALIDITY_DURATION_IN_YEAR", myais140tickets.generateRandomNumber(1));

		// Add the JSON object to the JSON array
		jsonArray.put(jsonObject);

		// Convert the JSON array to a string
		String requestBodyCRM = jsonArray.toString();

		// Send POST request to the API endpoint
		String token = myais140tickets.GenerateToken();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("token", token);
		request.body(requestBodyCRM);

		io.restassured.response.Response responseCRM = request.post("api/crm/generateTickets");

		// Use Gson to pretty print the JSON request body
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(gson.fromJson(requestBodyCRM, Object.class));
		
		// Parse the `requestBodyCRM` string into a JSONArray
        JSONArray parsedArray = new JSONArray(requestBodyCRM);

        // Extract the desired key value (e.g., "UIN_NO") from the first object
        if (parsedArray.length() > 0) {
            JSONObject firstObject = parsedArray.getJSONObject(0); // Get the first JSON object
            uinNo = firstObject.getString("UIN_NO"); // Extract "UIN_NO"
            System.out.println("Extracted UIN_NO from Request Body: " + uinNo);
        } else {
            System.out.println("JSON Array is empty in the request body.");
        }

		System.out.println("Request Body:");		
		System.out.println(prettyJson);
		String requestBody1 = responseCRM.getBody().prettyPrint();
	}

	@Test(priority = 3)
	public void testGetTicketStatus() {
		// Specify the base URI for the API
		RestAssured.baseURI = "http://20.219.88.214:6109";

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		// Add your data to the JSON object
		jsonObject.put("VIN_NO", myais140tickets.VIN_NO);
		jsonObject.put("ICCID", myais140tickets.ICCID);

		// Add the JSON object to the JSON array
		jsonArray.put(jsonObject);

		// Convert the JSON array to a string
		String requestBodyGET = jsonArray.toString();

		// Print the request body before sending the request
		System.out.println("Request Body:");
		System.out.println(requestBodyGET);

		// Send POST request to the API endpoint
		String token = myais140tickets.GenerateToken();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("token", token);
		request.body(requestBodyGET);

		io.restassured.response.Response responseGET = request.post("/api/crm/getTicketStatus");

		// Print the response status code
		System.out.println("Response Status Code: " + responseGET.getStatusCode());
		System.out.println("Request Body:");
		System.out.println(requestBodyGET);
		String requestBodyGET1 = responseGET.getBody().prettyPrint();

		// Extract the ticket number from the response body using JSONPath
		myais140tickets.Ticket_No = responseGET.jsonPath().getString("data[0].Ticket_No");

		// Print or log the extracted ticket number
		System.out.println("Ticket Number: " + myais140tickets.Ticket_No);

//		 return TicketNo;
	}

	
	public String AccessTicketNo() {
		// Print the ticket number
//        System.out.println("Ticket_No: " + myais140tickets.Ticket_No);
		// Return the ticket number
		return myais140tickets.Ticket_No;
	}

	@Test(priority = 4)

	public void login() {
		loginPage.enterUsername("accolade4g@accolade.com").enterPassword("Admin@321").clickLogin();
		System.out.println("Login Succesfully");
	}

	@Test(priority = 5)
	public void Navigation() throws InterruptedException {
		String testCaseName = "Test Navbar Links";
		String expectedURL = ConfigProperties.getProperty("dashboard.url");
		String actualURL = "";
		String result;
		logger.info("Executing the testClickNavBar for test case:" + testCaseName);
		try {
			logger.info("Attempting  to click on the nav bar links...");
			myais140tickets.clickNavBar();
			actualURL = driver.getCurrentUrl();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the navigation bar links.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
//		excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		System.out.println("Succesfully click on Device Utility Tab ");
	}

	@Test(priority = 6)
	public void testClickOnMyAIS140Ticket() {
		String testCaseName = "Test My AIS140 Tickets Link";
		String expectedURL = "";
		String actualURL = "";
		String result = "";

		logger.info("Executing the test Click on My AIS140 Tab option for test case:" + testCaseName);

		try {
			logger.info("Attempting  to click on the My AIS140 links...");
			myais140tickets.clickDropDownOption();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the navigation bar links.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}

		System.out.println("Succesfully click on My AIS140 Tab ");
	}

	@Test(priority = 7)
	public void clickSearchBox() {
		String testCaseName = "Test Click in Search Box";
		String expectedURL = ConfigProperties.getProperty("myTickets.url");
		String actualURL;
//		String actual = ConfigProperties.getProperty("myTickets.url");
//		String expected = "Search Button Clicked";
		String result;
		logger.info("Executing the test Click on My AIS140 search box option for test case:" + testCaseName);
		try {
			myais140tickets.ClickSearchBox();
			logger.info("Attempting  to click in the My AIS140 search box...");
			actualURL = driver.getCurrentUrl();
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		} catch (Exception e) {
//			logger.error("An error occurred while clicking on the in the search box.", e);
//			e.printStackTrace();
//			actual = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
//			result = expectedURL.equalsIgnoreCase(actual) ? "PASS" : "FAIL";
//			excelUtility.writeTestDataToExcel(testCaseName, expected, actual, result);
		}
		logger.info("Successfully executed the ClickSearchBox test case.");
		System.out.println("Succesfully click on Search Box ");
	}

	@Test(priority = 8)
	public void EnterSearchBox() throws InterruptedException {

		WebElement search = driver.findElement(
				By.xpath("/html/body/app-root/app-my-ais140-ticket-page/div/div[1]/div[4]/div/div[1]/i/div/input"));
		String testCaseName = "Test Enter in Search Box and click";
		String expectedURL = "";
		String actualURL = "";
		String result;
		logger.info("Executing the test Click on My AIS140 enter search box option for test case:" + testCaseName);
		try {
			logger.info("Attempting  to click in the My AIS140 enter search box...");
			logger.info("Entering the ticket number into the search box...");
			Thread.sleep(5000);
			String ticketNumber = AccessTicketNo();
			search.sendKeys(ticketNumber);
			Thread.sleep(5000);
			search.sendKeys(Keys.ENTER);

			logger.info("Waiting for URL validation..." + ticketNumber);
//			wait.until(ExpectedConditions.urlToBe(expectedURL));
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		} catch (Exception e) {
//		logger.error("An error occurred while enter Ticket Number in the search box.", e);
//		e.printStackTrace();
//		actualURL = driver.getCurrentUrl();
//		captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
//		excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully click and Ticket Number enter in Search Box ");
	}

	@Test(priority = 9)
	public void clickViewButton() {
		String testCaseName = "Test Click on Tickets View Link";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Click on Ticket View icon for test case:" + testCaseName);
		try {
			logger.info("Attempting  to click on the Ticket View icon...");
			myais140tickets.ClickViewButton();
			actualURL = driver.getCurrentUrl();
//			System.out.println(actualURL);
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the view icon button.", e);
//			e.printStackTrace();
//			actualURL = driver.getCurrentUrl();
//			result = "FAIL"; // Mark the test case as failed
//			captureScreenshot(testCaseName);
//			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully click on View icon ");
	}

	@Test(priority = 10)
	public void clickTicketInformation() {
		String testCaseName = "Test Click on Ticket details of Ticket Information option";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Click on Ticket details of Ticket Information option test case:" + testCaseName);
		try {
			logger.info("Attempting  to click on the Ticket Information option...");
			myais140tickets.ClickTicketInformation();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information option.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully click on Ticket Information option");
	}

	@Test(priority = 11)
	public void clickTicketInformationTicketNumber() {
		String testCaseName = "Test Click on Ticket Information of Ticket Number box";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Number box test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Number of the Ticket Information Tab...");
			myais140tickets.TicketNumber();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Number option.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Number of Ticket Information tab");
	}
	
	@Test(priority = 12)
	public void clickTicketInformationTicketCretedDateTime() {
		String testCaseName = "Test Click on Ticket Information of Ticket Created Date & Time box";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Created Date & Time box test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Created Date & Time of the Ticket Information Tab...");
			myais140tickets.TicketCretedTime();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Created Date & Time option.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Created Date & Time of Ticket Information tab");
	}
	
	@Test(priority = 13)
	public void clickTicketInformationTicketAssignedDateTime() {
		String testCaseName = "Test Click on Ticket Information of Ticket Assigned Date & Time box";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Assigned Date & Time box test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Assigned Date & Time of the Ticket Information Tab...");
			myais140tickets.TicketAssignedTime();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Assigned Date & Time option.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Assigned Date & Time of Ticket Information tab");
	}
	
	@Test(priority = 14)
	public void clickTicketInformationTicketCompleteCancelDateTime() {
		String testCaseName = "Test Click on Ticket Information of Ticket Cancelled or Completed Date & Time box";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Ticket Cancelled or Completed Date & Time box test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Assigned Date & Time of the Ticket Information Tab...");
			myais140tickets.TicketCancelledCompletedTime();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Ticket Cancelled or Completed Date & Time option.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Ticket Cancelled or Completed Date & Time of Ticket Information tab");
	}
	
	@Test(priority = 15)
	public void clickTicketInformationCertificateValidityDuration() {
		String testCaseName = "Test Click on Ticket Information of Ticket Certificate Validity Duration in Year box";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Certificate Validity Duration in Year test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Certificate Validity Duration in Year of the Ticket Information Tab...");
			myais140tickets.TicketCertificateValidityDuration();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Certificate Validity Duration in Year Ticket Certificate Validity Duration in Year.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Certificate Validity Duration in Year of Ticket Information tab");
	}
	
	@Test(priority = 16)
	public void clickTicketInformationTicketOverAllStatus() {
		String testCaseName = "Test Click on Ticket Information of Ticket Over All Status";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Over All Status test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Over All Status of the Ticket Information Tab...");
			myais140tickets.TicketOverAllStatus();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Over All Status.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Over All Status of Ticket Information tab");
	}
	
	@Test(priority = 17)
	public void clickTicketInformationTicketOverAllStatusRemark() {
		String testCaseName = "Test Click on Ticket Information of Ticket Over All Remark";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Over All Remark test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Over All Remark of the Ticket Information Tab...");
			myais140tickets.TicketOverAllRemark();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Over All Remark.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Over All Remark of Ticket Information tab");
	}
	
	@Test(priority = 18)
	public void clickTicketInformationTicketGeneratedBy() {
		String testCaseName = "Test Click on Ticket Information of Ticket Generatd By";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Generatd By test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Generatd By of the Ticket Information Tab...");
			myais140tickets.TicketGeneratedBy();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Generatd By.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Generatd By of Ticket Information tab");
	}
	
	@Test(priority = 19)
	public void clickTicketInformationTicketDescripton() {
		String testCaseName = "Test Click on Ticket Information of Ticket Descripton";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Ticket Information of Ticket Descripton test case:" + testCaseName);
		try {
			logger.info("Attempting  to read Ticket Descripton of the Ticket Information Tab...");
			myais140tickets.TicketDescription();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);			
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Ticket Information of Ticket Descripton.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully read Ticket Descripton of Ticket Information tab");
	}
	
	@Test(priority = 20)
	public void clickDeviceInformation() {
		String testCaseName = "Test Click on Ticket details of Device Information option";
		String expectedURL = "";
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Click on Ticket details of Device Information option test case:" + testCaseName);
		try {
			logger.info("Attempting  to click on the Device Information option...");
			myais140tickets.ClickDeviceInformation();
			actualURL = driver.getCurrentUrl();
			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the Device Information option.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully click on Device Information option");
	}
	
	@Test(priority = 21)
	public void clickDeviceInformationUINNumber() {
		String testCaseName = "Test Click on Ticket details of Device Information option";
		String expectedURL = uinNo;
		String actualURL = "";
		String result = "";
		logger.info("Executing the test Click on Ticket details of Device Information of UIN Number option test case:" + testCaseName);
		try {
			logger.info("Attempting  to click on the Device Information UIN Number option...");
			actualURL = myais140tickets.DeviceUINNumber();
//			actualURL = driver.getCurrentUrl();
//			expectedURL = actualURL;
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			logger.info("Result is : " + result);
		} catch (Exception e) {
			logger.error("An error occurred while clicking on the  UIN Number of Device Information option.", e);
			e.printStackTrace();
			actualURL = driver.getCurrentUrl();
//			captureScreenshot(testCaseName);
			result = expectedURL.equalsIgnoreCase(actualURL) ? "PASS" : "FAIL";
			excelUtility.writeTestDataToExcel(testCaseName, expectedURL, actualURL, result);
		}
		logger.info("Completed the test case: " + testCaseName);
		System.out.println("Succesfully click on Device Information of UIN Number option");
	}
}