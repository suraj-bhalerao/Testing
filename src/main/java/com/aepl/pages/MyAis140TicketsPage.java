package com.aepl.pages;

import static io.restassured.RestAssured.given;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MyAis140TicketsPage {
	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(MyAis140TicketsPage.class);

	public MyAis140TicketsPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public String VIN_NO = generateRandomString(17);
	public String ICCID = "89916490634628942181";
	public String Ticket_No; // Global variable to store the generated Ticket Number

	public String GenerateToken() {
		HashMap<String, String> data = new HashMap<>();
		data.put("username", "accoladeCrm");
		data.put("password", "admin@123");

		Response response = given().contentType("application/json").body(data).when()
				.post("http://20.219.88.214:6109/api/crm/generateToken").then().statusCode(200).log().all().extract()
				.response();

		// Extract the token from the response and assign it to the token variable
		String token = response.jsonPath().getString("token");

		// Return the token
		return token;
	}

// Locators Goes here
	private By navBarLink = By.xpath("//span[@class=\"headers_custom color_3D5772\"]");
	private By MyAis140 = By.xpath("//*[@id=\"navbarSupportedContent\"]/div/ul/li[2]/div/a[1]");
	private By SearchBox = By
			.xpath("/html/body/app-root/app-my-ais140-ticket-page/div/div[1]/div[4]/div/div[1]/i/div/input");
	private By tableHeadings = By.xpath("//tr[@class=\\\"text-center\\\"]");

	private By overlay = By.cssSelector(".overlay");
	private By viewButton = By.xpath("//*[@id=\"DataTables_Table_0\"]/tbody/tr/td[12]/i");
	private By Arrow = By.xpath("//div[@class=\"thumb ng-star-inserted\"]"); 
	private By TicketNumber = By.id("mat-input-22");
	private By TicketCreate = By.id("mat-input-23");
	private By TicketAssigned = By.id("mat-input-24");
	private By TicketCompleted = By.id("mat-input-25");
	private By TicketCertificate = By.id("mat-input-26");
	private By TicketStatus = By.id("mat-input-27");
	private By TicketRemark = By.id("mat-input-28");
	private By TicketGenrate = By.id("mat-input-29");
	private By TicketDesc = By.id("mat-input-30");

	
	public String generateRandomString(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			char randomChar = characters.charAt(index);
			stringBuilder.append(randomChar);
		}

		return stringBuilder.toString();

	}

	// Utility method to generate a random date in "yyyy-MM-dd" format
	public String generateRandomDate() {
		Random random = new Random();
		int year = random.nextInt(10) + 2020; // Random year between 2020 and 2029
		int month = random.nextInt(12) + 1; // Random month between 1 and 12
		int day = random.nextInt(28) + 1; // Random day between 1 and 28 (to avoid issues with different month lengths)

		// Create a Calendar instance and set the random year, month, and day
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1); // Calendar months are 0-based (0 for January)
		calendar.set(Calendar.DAY_OF_MONTH, day);

		// Format the date in "yyyy-MM-dd" format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}

	// Utility method to generate a random year
	public int generateRandomYear(int lenghth) {
		Random random = new Random();
		return random.nextInt(10) + 2020; // Random year between 2020 and 2029

	}

	// Utility method to generate a random number within a specified range
	public String generateRandomNumber(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		// Ensure the first digit is not zero to make it a valid mobile number
		sb.append(random.nextInt(9) + 1);
		for (int i = 1; i < length; i++) {
			sb.append(random.nextInt(10));
		}

		return sb.toString();
	}

	public int generateRandomSingleDigit() {
		Random random = new Random();
		return random.nextInt(10); // Returns a number between 0 and 9
	}

	public LocalDate generateYesterdayDate() {
		LocalDate today = LocalDate.now(); // Get today's date
		LocalDate yesterday = today.minusDays(1); // Subtract one day
		return yesterday; // Return yesterday's date
	}

	public LocalDate generateFutureDate() {
		LocalDate today = LocalDate.now(); // Get today's date
		LocalDate futureDate = today.plusYears(2); // Add two years
		return futureDate; // Return the future date
	}

//	@Test(priority = 1)
	public void testRequestBodyWithRandomValuesCRMData() {
		// Generate random values for the request body
		String VIN_NO = "VIN_NO";
		String ICCID = "ICCID";
		String UIN_NO = generateRandomString(19);
		String DEVICE_IMEI = generateRandomNumber(15);
		String DEVICE_MAKE = "Accolade";
		String DEVICE_MODEL = "AEPL051401";
		String ENGINE_NO = generateRandomString(25);
		String REG_NUMBER = generateRandomString(15);
		String VEHICLE_OWNER_FIRST_NAME = generateRandomString(15);
		String VEHICLE_OWNER_MIDDLE_NAME = generateRandomString(15);
		String VEHICLE_OWNER_LAST_NAME = generateRandomString(15);
		String ADDRESS_LINE_1 = generateRandomString(50);
		String ADDRESS_LINE_2 = generateRandomString(50);
		String VEHICLE_OWNER_CITY = generateRandomString(15);
		String VEHICLE_OWNER_DISTRICT = generateRandomString(15);
		String VEHICLE_OWNER_STATE = generateRandomString(15);
		String VEHICLE_OWNER_COUNTRY = generateRandomString(15);
		String VEHICLE_OWNER_PINCODE = generateRandomNumber(6);
		String VEHICLE_OWNER_REGISTERED_MOBILE = generateRandomNumber(10);
		String POS_CODE = generateRandomString(10);
		String POA_DOC_NAME = generateRandomString(10);
		String POA_DOC_NO = generateRandomString(10);
		String POI_DOC_TYPE = generateRandomString(20);
		String POI_DOC_NO = generateRandomString(10);
		String RTO_OFFICE_CODE = generateRandomString(10);
		String RTO_STATE = generateRandomString(10);
		String PRIMARY_OPERATOR = "BSNL";
		String SECONDARY_OPERATOR = "BHA";
		String PRIMARY_MOBILE_NUMBER = generateRandomNumber(15);
		String SECONDARY_MOBILE_NUMBER = generateRandomNumber(15);
		String VEHICLE_MODEL = generateRandomString(15);
		String DEALER_CODE = generateRandomString(15);
		LocalDate COMMERCIAL_ACTIVATION_START_DATE = generateYesterdayDate();
		LocalDate COMMERCIAL_ACTIVATION_EXPIRY_DATE = generateFutureDate();
		int MFG_YEAR = generateRandomYear(4);
		String INVOICE_DATE = generateRandomDate();
		String INVOICE_NUMBER = generateRandomString(20);
		int CERTIFICATE_VALIDITY_DURATION_IN_YEAR = 2;
	}

//	@Test(priority = 2)
	void SaveCRMData() {
		RestAssured.baseURI = "http://20.219.88.214:6109";

		// Inside your SaveCRMData method
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		// Add your data to the JSON object
		jsonObject.put("VIN_NO", VIN_NO);
		jsonObject.put("ICCID", ICCID);
		jsonObject.put("UIN_NO", generateRandomString(19));
		jsonObject.put("DEVICE_IMEI", generateRandomNumber(15));
		jsonObject.put("DEVICE_MAKE", "Accolade");
		jsonObject.put("DEVICE_MODEL", "AEPL051400");
		jsonObject.put("ENGINE_NO", generateRandomString(50));
		jsonObject.put("REG_NUMBER", generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_FIRST_NAME", generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_MIDDLE_NAME", generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_LAST_NAME", generateRandomString(15));
		jsonObject.put("ADDRESS_LINE_1", generateRandomString(20));
		jsonObject.put("ADDRESS_LINE_2", generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_CITY", generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_DISTRICT", generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_STATE", generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_COUNTRY", generateRandomString(15));
		jsonObject.put("VEHICLE_OWNER_PINCODE", generateRandomNumber(6));
		jsonObject.put("VEHICLE_OWNER_REGISTERED_MOBILE", generateRandomNumber(10));
		jsonObject.put("DEALER_CODE", generateRandomNumber(6));
		jsonObject.put("POS_CODE", generateRandomString(15));
		jsonObject.put("POA_DOC_NAME", generateRandomString(15));
		jsonObject.put("POA_DOC_NO", generateRandomString(15));
		jsonObject.put("POI_DOC_TYPE", generateRandomString(15));
		jsonObject.put("POI_DOC_NO", generateRandomString(15));
		jsonObject.put("RTO_OFFICE_CODE", generateRandomString(15));
		jsonObject.put("RTO_STATE", generateRandomString(15));
		jsonObject.put("PRIMARY_OPERATOR", "BSNL");
		jsonObject.put("SECONDARY_OPERATOR", "BHA");
		jsonObject.put("PRIMARY_MOBILE_NUMBER", generateRandomNumber(15));
		jsonObject.put("SECONDARY_MOBILE_NUMBER", generateRandomNumber(15));
		jsonObject.put("VEHICLE_MODEL", generateRandomString(15));
		jsonObject.put("COMMERCIAL_ACTIVATION_START_DATE", generateYesterdayDate());
		jsonObject.put("COMMERCIAL_ACTIVATION_EXPIRY_DATE", generateFutureDate());
		jsonObject.put("MFG_YEAR", generateRandomYear(4));
		jsonObject.put("INVOICE_DATE", generateRandomDate());
		jsonObject.put("INVOICE_NUMBER", generateRandomString(15));
		jsonObject.put("CERTIFICATE_VALIDITY_DURATION_IN_YEAR", generateRandomNumber(1));

		// Add the JSON object to the JSON array
		jsonArray.put(jsonObject);

		// Convert the JSON array to a string
		String requestBodyCRM = jsonArray.toString();

		// Send POST request to the API endpoint
		String token = GenerateToken();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("token", token);
		request.body(requestBodyCRM.toString());

		io.restassured.response.Response responseCRM = request.post("api/crm/generateTickets");

		// Use Gson to pretty print the JSON request body
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(gson.fromJson(requestBodyCRM, Object.class));

		System.out.println("Request Body:");
		System.out.println(prettyJson);
		String requestBody1 = responseCRM.getBody().prettyPrint();
	}

//	@Test(priority = 3)
	public void testGetTicketStatus() {
		// Specify the base URI for the API
		RestAssured.baseURI = "http://20.219.88.214:6109";

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		// Add your data to the JSON object
		jsonObject.put("VIN_NO", VIN_NO);
		jsonObject.put("ICCID", ICCID);

		// Add the JSON object to the JSON array
		jsonArray.put(jsonObject);

		// Convert the JSON array to a string
		String requestBodyGET = jsonArray.toString();

		// Print the request body before sending the request
		System.out.println("Request Body:");
		System.out.println(requestBodyGET);

		// Send POST request to the API endpoint
		String token = GenerateToken();
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
		Ticket_No = responseGET.jsonPath().getString("data[0].Ticket_No");

		// Print or log the extracted ticket number
		System.out.println("Ticket Number: " + Ticket_No);

//		 return TicketNo;
	}

	public void clickNavBar() {
		// Wait for the navigation bar links to be visible
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navBarLink));

		boolean isClicked = false;
		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Device Utility")) {
				link.click();
//				System.out.println("Clicked On Element On Nav: " +link.getAccessibleName());
				isClicked = true;
//				break;
			}
		}
		if (!isClicked) {
			throw new RuntimeException("Failed to find and click on 'Device Utility' in the navigation bar.");
		}
	}

	public String clickDropDownOption() {
		// Click on the element 'My AIS140 Tickets Page' and return the current URL
		try {
			WebElement MyAis140Ticket = wait.until(ExpectedConditions.visibilityOfElementLocated(MyAis140));
			Thread.sleep(2000);
			MyAis140Ticket.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on My AIS140 Ticket option.", e);
			throw new RuntimeException("Failed to click on My AIS140 Ticket option", e);
		}
	}

	public WebElement ClickSearchBox() {
		try {
			// Wait for the navigation bar links to be visible
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(SearchBox));
			Thread.sleep(2000);
//				search.click();
			js.executeScript("arguments[0].style.border='5px solid Yellow'");
			return search;
		} catch (Exception e) {
			logger.error("Error while clicking on My AIS140 Ticket Search Box option.", e);
			throw new RuntimeException("Failed to click on My AIS140 Ticket Search Box option", e);
		}
	}

	public void ClickViewButton() throws InterruptedException {
		try {
			// Wait for the overlay to disappear
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));

			// Find and click the element
			WebElement elementAtZeroIndex = driver.findElement(viewButton);
			elementAtZeroIndex.click();

			logger.info("Successfully clicked on the View button.");
			Thread.sleep(5000);

			// Optionally navigate back
//	        driver.navigate().back();
		} catch (Exception e) {
			logger.error("Error while clicking the View button.", e);
			throw new RuntimeException("Failed to click the View button.", e);
		}
	}

	public void ClickTicketInformation() {
		int tabIndex=1;
		  try {
		        // Wait for the overlay to disappear (if any)
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		        wait.until(ExpectedConditions.invisibilityOfElementLocated(Arrow));		        
		        String originalTab = driver.getWindowHandle(); 
		        logger.info("Original tab handle stored: " + originalTab);
		        Set<String> allTabs = driver.getWindowHandles();
		        ArrayList<String> tabList = new ArrayList<>(allTabs);
		        if (tabIndex < 0 || tabIndex >= tabList.size()) {
		            logger.error("Invalid tab index: " + tabIndex + ". Total tabs open: " + tabList.size());
		            throw new RuntimeException("Invalid tab index: " + tabIndex);		    
		            }
		        String targetTab = tabList.get(tabIndex);
		        logger.info("Switching to tab with index: " + tabIndex + ", handle: " + targetTab);
		        driver.switchTo().window(targetTab);
		        // Wait for the particular element in the new tab to be clickable
		        WebElement elementToClickInNewTab = wait.until(ExpectedConditions.elementToBeClickable(Arrow));
		        elementToClickInNewTab.click();
		        logger.info("Successfully clicked the Ticket Information of the new tab.");
		    } catch (Exception e) {
		        logger.error("Error while handling the new window/tab.", e);
		        throw new RuntimeException("Failed to interact with the new window/tab.", e);
		    }
	}
	
	public void TicketNumber() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// Wait for the element to be visible
			WebElement ticketNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketNumber));

			// Scroll the element into view if necessary
//    	js.executeScript("arguments[0].scrollIntoView(true);", ticketNumberElement);

    	// Simulate a click using JavaScript if the element is not natively clickable
    	js.executeScript("arguments[0].click();", ticketNumberElement);

    	// Retrieve the "value" attribute
    	String inputValue = ticketNumberElement.getAttribute("value");
    
    	// Print Ticket Information
    	System.out.println("\u001B[1m\u001B[34mTicket Information:\u001B[0m");
    	System.out.println("\u001B[1m\u001B[35mTicket Number:\u001B[0m " + inputValue);

    	// Highlight the element for visual verification
    	js.executeScript("arguments[0].style.border='5px solid yellow';", ticketNumberElement);

		} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Number.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Number.", e);
		}
	}
	
	public void TicketCretedTime() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// Wait for the element to be visible
			WebElement ticketCreateTimeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketCreate));
			js.executeScript("arguments[0].click();", ticketCreateTimeElement);
			String inputValue = ticketCreateTimeElement.getAttribute("value");   
			System.out.println("\u001B[1m\u001B[35mTicket Created Time & Date :\u001B[0m " + inputValue);    	
			js.executeScript("arguments[0].style.border='5px solid yellow';", ticketCreateTimeElement);
		  	} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Created Time & Date.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Created Time & Date.", e);
		}
	}
	
	public void TicketAssignedTime() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement ticketAssignTimeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketAssigned));
			js.executeScript("arguments[0].click();", ticketAssignTimeElement);
			String inputValue = ticketAssignTimeElement.getAttribute("value");   
   			System.out.println("\u001B[1m\u001B[35mTicket Assigned Time & Date :\u001B[0m " + inputValue);
			js.executeScript("arguments[0].style.border='5px solid yellow';", ticketAssignTimeElement);
			} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Assigned Time & Date.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Assigned Time & Date.", e);
		}
	}
	
	public void TicketCancelledCompletedTime() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement ticketCompleteCancelElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketCompleted));
			js.executeScript("arguments[0].click();", ticketCompleteCancelElement);    	
			String inputValue = ticketCompleteCancelElement.getAttribute("value");   
			System.out.println("\u001B[1m\u001B[35mTicket Cancelled or Completed Time & Date :\u001B[0m " + inputValue);
			js.executeScript("arguments[0].style.border='5px solid yellow';", ticketCompleteCancelElement);
			} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Cancelled or Completed Time & Date.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Cancelled or Completed Time & Date.", e);
		}
	}
	
	public void TicketCertificateValidityDuration() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			// Wait for the element to be visible
			WebElement ticketCertificateValidityDurationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketCertificate));
			js.executeScript("arguments[0].click();", ticketCertificateValidityDurationElement);
			String inputValue = ticketCertificateValidityDurationElement.getAttribute("value");   
			System.out.println("\u001B[1m\u001B[35mTicket Certificate Validity Duration in Year :\u001B[0m " + inputValue);
			js.executeScript("arguments[0].style.border='5px solid yellow';", ticketCertificateValidityDurationElement);
			} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Certificate Validity Duration in Year.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Certificate Validity Duration in Year.", e);
		}
	}
	
	public void TicketOverAllStatus() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement ticketOverAllStatusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketStatus));
			js.executeScript("arguments[0].click();", ticketOverAllStatusElement);    	
			String inputValue = ticketOverAllStatusElement.getAttribute("value");      	
			System.out.println("\u001B[1m\u001B[35mTicket Over All Status :\u001B[0m " + inputValue); 
			js.executeScript("arguments[0].style.border='5px solid yellow';", ticketOverAllStatusElement);
			} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Over All Status.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Over All Status.", e);
		}
	}
	
	public void TicketOverAllRemark() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement ticketOverAllRemarkElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketRemark));
			js.executeScript("arguments[0].click();", ticketOverAllRemarkElement);    	
			String inputValue = ticketOverAllRemarkElement.getAttribute("value");       	
			System.out.println("\u001B[1m\u001B[35mTicket Over All Remark :\u001B[0m " + inputValue);    
			js.executeScript("arguments[0].style.border='5px solid yellow';", ticketOverAllRemarkElement);
			} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Over All Remark.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Over All Remark.", e);
		}
	}
	
	public void TicketGeneratedBy() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;			
			WebElement ticketGeneratedByElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketGenrate));
			js.executeScript("arguments[0].click();", ticketGeneratedByElement);
			String inputValue = ticketGeneratedByElement.getAttribute("value");   
			System.out.println("\u001B[1m\u001B[35mTicket Generated By :\u001B[0m " + inputValue);
			js.executeScript("arguments[0].style.border='5px solid yellow';", ticketGeneratedByElement);
			} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Generated By.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Generated By.", e);
		}
	}

	public void TicketDescription() { 
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;			
			WebElement ticketDescriptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TicketDesc));
			js.executeScript("arguments[0].click();", ticketDescriptionElement);
			String inputValue = ticketDescriptionElement.getAttribute("value");   
			System.out.println("\u001B[1m\u001B[35mTicket Description :\u001B[0m " + inputValue);
			js.executeScript("arguments[0].style.border='5px solid yellow';", ticketDescriptionElement);
			} catch (Exception e) {
			logger.error("Error while interacting with Ticket Information for Ticket Description.", e);
			throw new RuntimeException("Failed to read Ticket Information for Ticket Description.", e);
		}
	}
	
	public By getArrowByIndex(int index) {
	    return By.xpath("(//div[@class='thumb ng-star-inserted'])[" + index + "]");
	}
	
	public void ClickDeviceInformation() {
		int tabIndex=1;
		  try {
//		        // Wait for the overlay to disappear (if any)
//		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//		        wait.until(ExpectedConditions.invisibilityOfElementLocated(Arrow));		        
//		        String originalTab = driver.getWindowHandle(); 
//		        logger.info("Original tab handle stored: " + originalTab);
//		        Set<String> allTabs = driver.getWindowHandles();
//		        ArrayList<String> tabList = new ArrayList<>(allTabs);
//		        if (tabIndex < 0 || tabIndex >= tabList.size()) {
//		            logger.error("Invalid tab index: " + tabIndex + ". Total tabs open: " + tabList.size());
//		            throw new RuntimeException("Invalid tab index: " + tabIndex);		    
//		            }
//		        String targetTab = tabList.get(tabIndex);
//		        logger.info("Switching to tab with index: " + tabIndex + ", handle: " + targetTab);
//		        driver.switchTo().window(targetTab);
//		        // Wait for the particular element in the new tab to be clickable
//		        
//		        int desiredIndex = 2; // 1-based index for the desired element
		       
		        WebElement elementToClickInNewTab1 = wait.until(ExpectedConditions.elementToBeClickable(Arrow));
//		        WebElement elementToClickInNewTab = driver.findElement(getArrowByIndex(desiredIndex));
		        elementToClickInNewTab1.click();
		        logger.info("Successfully clicked the Device Information of the new tab.");
		    } catch (Exception e) {
		        logger.error("Error while clicking on the Device Information Tab.", e);
		        throw new RuntimeException("Failed to clicking on the Device Information Tab.", e);
		    }
	}
}
