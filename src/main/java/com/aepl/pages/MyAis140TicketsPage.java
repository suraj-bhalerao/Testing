package com.aepl.pages;

import static io.restassured.RestAssured.given;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MyAis140TicketsPage {
	private static final Collection<?> CERTIFICATE_VALIDITY_DURATION_IN_YEAR = null;
	protected String VIN_NO = generateRandomString(17);
	protected String ICCID = "89916490634628942181";
	public String Ticket_No; // Global variable to store the generated Ticket Number
	private WebDriver driver;
	private String baseUrl = "http://20.219.88.214:6102/login";

	@Test
	String GenerateToken() {
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

	@Test(priority = 1)
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

	@Test(priority = 2)
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

	@Test(priority = 3)
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
}
