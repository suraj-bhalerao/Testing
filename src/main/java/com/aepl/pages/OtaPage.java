package com.aepl.pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aepl.actions.CalendarActions;
import com.aepl.util.CommonMethod;

public class OtaPage {

	// Global variables
	private WebDriver driver;
	private WebDriverWait wait;
	private CommonMethod commonMethod;
	private CalendarActions calendarActions;
<<<<<<< HEAD
=======
	private MouseActions mouseActions;
>>>>>>> 83a3fa5fe0e80bce550fd50eae3ca8c088dccca2
	private static final Logger logger = LogManager.getLogger(OtaPage.class);

	// Constructor
	public OtaPage(WebDriver driver) {
		this.driver = driver;
		this.commonMethod = new CommonMethod(driver);
		this.calendarActions = new CalendarActions(driver);
<<<<<<< HEAD
=======
		this.mouseActions = new MouseActions(driver);
>>>>>>> 83a3fa5fe0e80bce550fd50eae3ca8c088dccca2
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Variables
	public String batchCount = "";

	// Locators
	private By navBarLink = By.xpath("//span[@class='headers_custom color_3D5772']");
	private By otaLink = By.xpath("//a[@class='dropdown-item ng-star-inserted'][4]");
	private By buttonsList = By.xpath("//button[@class='btn btn-outline-primary ng-star-inserted']");
	private By nextBtn = By.xpath("//a[@class=\"ng-star-inserted\"]");
	private By prevBtn = By.xpath("//a[@class=\"ng-star-inserted\"]");
	private By activeBtn = By.xpath("//a[@class=\"ng-star-inserted\"]");
	private By eyeActionButton = By.xpath("//mat-icon[@mattooltip='View']");
	private By calendar = By.xpath("//button[@class=\"mat-focus-indicator mat-icon-button mat-button-base\"]");
	private By batchIdFrom = By.id("fromBatchId");
	private By batchIdTo = By.id("toBatchId");
	private By batchSubmitBtn = By.xpath("//button[@class=\"btn-sm btn btn-outline-primary example-full-width\"]");
	private By clearButton = By.xpath("//button[@class=\"btn-sm btn btn-outline-secondary example-full-width\"]");
	public By reportButton = By.xpath("//button[@class=\"btn-sm btn example-full-width float-right\"]");
<<<<<<< HEAD
=======
	private By allInputFields = By.tagName("input");
	private By toastMessageOfOtaAdd = By.xpath("//simple-snack-bar//span[text()='Success']");
	private By editButtonOfOta = By
			.xpath("//mat-icon[@class=\"mat-icon notranslate mx-2 material-icons mat-icon-no-color\"]");
	private By deleteButtonOfOta = By.xpath(
			"//mat-icon[class=\"mat-icon notranslate mat-tooltip-trigger delete-icon material-icons mat-icon-no-color\"]");
>>>>>>> 83a3fa5fe0e80bce550fd50eae3ca8c088dccca2

	// Methods
	public void clickNavBar() {
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navBarLink));

		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Device Utility")) {
				link.click();
				return;
			}
		}
		throw new RuntimeException("Failed to find and click on 'Device Utility' in the navigation bar.");
	}

	public String clickDropdown() {
		try {
			WebElement changeMobileLink = wait.until(ExpectedConditions.visibilityOfElementLocated(otaLink));
			changeMobileLink.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Change Mobile option.", e);
			throw new RuntimeException("Failed to click on Change Mobile option", e);
		}
	}

	public void checkButtons() {
		try {
			List<WebElement> btnList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(buttonsList));
			logger.info("Found " + btnList.size() + " buttons on the page.");

			for (int i = 0; i < btnList.size(); i++) {
				WebElement btn = btnList.get(i);

				if (btn.isDisplayed() && btn.isEnabled()) {
					logger.info("Clicking button " + (i + 1) + " with text: " + btn.getText());
					btn.click();
					logger.info("Navigated to URL: " + driver.getCurrentUrl());
					driver.navigate().back();
					logger.info("Navigated back to the original page.");
					btnList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(buttonsList));
				} else {
					logger.warn("Button " + (i + 1) + " is either not displayed or not enabled.");
				}
			}

			logger.info("Successfully clicked all buttons on the page.");
		} catch (Exception e) {
			logger.error("An error occurred while interacting with buttons.", e);
			throw new RuntimeException("Failed to interact with all buttons.", e);
		}
	}

	public boolean checkSearchBoxAndTable() {
		logger.log(Level.INFO, "Trying to check the search box and table");
		String batchName = "SB_OTA_TEST";
		List<String> expectedHeaders = Arrays.asList("Batch ID", "Batch Name", "Batch Description", "Created By",
				"Created At", "Batch Breakdown", "Completed Percentage", "Batch Status", "Action");

		logger.log(Level.INFO, "Taking table heading before the search");

		// this is to get the latest batch number of the ota table.
		batchCount = driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]")).getText();

		return commonMethod.checkSearchBoxWithTableHeadings(batchName, expectedHeaders);
	}

	public void checkActionButtons() {
		logger.log(Level.INFO, "Checking the eye action button");
		try {
			driver.switchTo().activeElement().findElement(By.xpath("//table/tbody/tr/td[9]"));
			commonMethod.clickEyeActionButton(eyeActionButton);
		} catch (Exception e) {
			logger.error("Error while clicking on the eye action button", e);
		}
		logger.log(Level.INFO, "Clicked on the eye action button");
	}

	public void checkPagination() {
		try {
			WebElement nextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(nextBtn));
			WebElement prevButton = wait.until(ExpectedConditions.visibilityOfElementLocated(prevBtn));
			WebElement actButton = wait.until(ExpectedConditions.visibilityOfElementLocated(activeBtn));

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", nextButton);

			Thread.sleep(2000);

			logger.log(Level.INFO, "checking the pagination button here");
			commonMethod.checkPagination(nextButton, prevButton, actButton);
			logger.log(Level.INFO, "log after checking the pagination");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void clickOtaBatchReport() {
		// checking the ota batch report button
		try {
			driver.navigate().refresh();

			List<WebElement> buttonList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(buttonsList));

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", buttonList.get(0));

			Thread.sleep(2000);

			for (WebElement button : buttonList) {
				if (button.getText().equalsIgnoreCase("OTA Batch Report")) {
					button.click();
					logger.info("Clicked on the OTA Batch Report button");
					System.out.println("Navigated to URL: " + driver.getCurrentUrl());
					return;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void getOtaBatchDateWise() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(calendar));
			WebElement fromBatch = wait.until(ExpectedConditions.visibilityOfElementLocated(batchIdFrom));
			WebElement toBatch = wait.until(ExpectedConditions.visibilityOfElementLocated(batchIdTo));
			WebElement submitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(batchSubmitBtn));
			WebElement clearBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(clearButton));

			for (int i = 0; i < 4; i++) {
				// From Date
				calendarActions.selectDate(calendar, "31-01-2025");
				logger.info("Selected the date from the calendar");

				// To Date
				calendarActions.selectDate(calendar, "03-02-2025");
				logger.info("Selected the date from the calendar");

				int toCount = Integer.parseInt(batchCount) - 20;

				Thread.sleep(1000);
				fromBatch.click();
				fromBatch.sendKeys(String.valueOf(toCount));

				Thread.sleep(1000);
				toBatch.click();
				toBatch.sendKeys(String.valueOf(batchCount));

				// After the loop end submitting the value
				if (i == 1) {
					Thread.sleep(1000);
					submitBtn.click();
					break;
				}
				// in first iteration press on clear
				Thread.sleep(1000);
				clearBtn.click();
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void checktableHeading() {
		logger.info("Starting to check table headings.");

		List<String> expectedHeadings = Arrays.asList("Batch ID", "Batch Type", "Batch Date", "Total Device Uploaded",
				"Total Device Completed", "Total Device Aborted", "Total Device In-progress/Pending",
				"Batch Percentage");

		logger.info("Expected table headings: " + expectedHeadings);

		boolean result = commonMethod.checkTableHeadings(expectedHeadings);

		if (result) {
			logger.info("Table headings are as expected.");
		} else {
			logger.warn("Table headings do not match the expected values.");
		}
	}

	public boolean checkReportsButtons() {
		logger.info("Starting to check report buttons.");

		try {
			List<WebElement> reportButtons = wait
					.until(ExpectedConditions.presenceOfAllElementsLocatedBy(reportButton));
			logger.info("Found " + reportButtons.size() + " report buttons.");

			for (WebElement button : reportButtons) {
				if (button.isEnabled() && button.isDisplayed()) {
					Thread.sleep(2000);
					System.out.println("Button text: " + button.getText());
					if (button.getText().contains("Batch Summary Report")) {
						commonMethod.reportDownloadButtons(button);
						return true;
					} else {
						logger.warn("Button text does not match the expected value.");
					}
				} else {
					logger.warn("Button is either not enabled or not displayed.");
				}
			}
		} catch (Exception e) {
			logger.error("An error occurred while checking report buttons.", e);
		}

		logger.info("Finished checking report buttons. No clickable button found.");
		driver.navigate().back();
		return false;
	}

	public String clickOtaMaster() {
		try {
<<<<<<< HEAD
=======
			driver.navigate().back();
			Thread.sleep(1000);
>>>>>>> 83a3fa5fe0e80bce550fd50eae3ca8c088dccca2
			List<WebElement> buttonList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(buttonsList));

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", buttonList.get(1));

			Thread.sleep(2000);

			for (WebElement button : buttonList) {
				if (button.getText().equalsIgnoreCase("OTA Master")) {
					button.click();
					logger.info("Clicked on the OTA Master button");
					System.out.println("Navigated to URL: " + driver.getCurrentUrl());
					return driver.getCurrentUrl();
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "link not found";
	}
<<<<<<< HEAD
	
	// all other methods here
	public void addNewOta() {
		// Add all types of all ota one by one like 
		/*
		 *  *SET#Example# /
		 	*SET#Example#VAL# /
		 	*SET#Example#VAL#VAL /
		 	*GET#Example# /
		 	*CLR#Example#
		 * */
	}
	
	public void checkSearchAndTable() {
		// step 1: search ota that is added
		// step 2: check the table
		
	}
	
	public void checkOtaMasterActionButtons() {
		// step 1: search ota that is added 
		// step 2: click on the edit button
		// step 3: edit the same ota
		// step 4: come back 
		// step 5: click on the delete button
	}
	
	public void checkOtaMasterPagination() {
		// step 1: check the pagination
		
		// NOTE : you have to update the logic of the pagination firstly
	}
	
=======

	public String fillAndSubmitOtaForm() {
		WebElement addButton = driver
				.findElement(By.xpath("//button[@class=\"btn btn-outline-primary ng-star-inserted\"]"));
		WebElement toastConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessageOfOtaAdd));

		// Update Button element when it is called
		WebElement updateButton = driver
				.findElement(By.xpath("//button[@class=\"btn btn-outline-primary ng-star-inserted\"]"));

		try {
			List<WebElement> inputFields = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allInputFields));
			for (WebElement inputField : inputFields) {
				String placeholder = inputField.getDomAttribute("placeholder");
				switch (placeholder) {
				case "Please Enter OTA Command Name":
					inputField.sendKeys("DEMO");
					Thread.sleep(1000);
					break;
				case "Please Enter OTA Command":
					inputField.sendKeys("*GET#CIIP1#");
					Thread.sleep(1000);
					break;
				case "Please Enter Keyword":
					inputField.sendKeys("CIP1");
					Thread.sleep(1000);
					break;
				case "Please Enter Example":
					inputField.sendKeys("*GET#Example#");
					Thread.sleep(1000);
					break;
				case "Please Enter Min Value":
					inputField.sendKeys("0");
					Thread.sleep(1000);
					break;
				case "Please Enter Max Value":
					inputField.sendKeys("1");
					Thread.sleep(1000);
					break;
				default:
					logger.warn("Unknown placeholder: " + placeholder);
				}
			}
			Thread.sleep(2000);

			if (addButton.isDisplayed() && addButton.isEnabled()) {
				addButton.click();
				Thread.sleep(2000);
				return toastConfirmation.getText();
			}else {
				updateButton.click();
				Thread.sleep(2000);
				return toastConfirmation.getText();
			}
		} catch (Exception e) {
			logger.error("An error occurred while filling and submitting the OTA form.", e);
		}
		return "No toast message found";
	}

	public boolean checkSearchAndTableOfOtaMaster() {
		String searchInput = "*GET#CIIP1#";
		List<String> expectedHeaders = Arrays.asList("OTA Command Name", "OTA Command", "Keyword", "Example", "Min",
				"Max", "Action");
		return commonMethod.checkSearchBoxWithTableHeadings(searchInput, expectedHeaders);
	}

	public void checkOtaMasterActionButtons() {
		// step 1: search ota that is added
		String searchInput = "*GET#CIIP1#";
		try {
			commonMethod.checkSearchBox(searchInput);
			Thread.sleep(1000);
			// step 2: click on the edit button
			WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonOfOta));
			editButton.click();
			Thread.sleep(1000);
			// step 3: edit the same ota
			String updateMessage = fillAndSubmitOtaForm();
			boolean isOtaUpdate = updateMessage.contains("Successfully updated.");
			System.out.println("OTA UPDATED ? " + isOtaUpdate);
			if (isOtaUpdate) {
				logger.info("OTA command updated successfully.");
			} else {
				logger.warn("OTA command not updated.");
			}
			// step 5: click on the delete button
			mouseActions.moveToElement(driver.findElement(deleteButtonOfOta));
			Thread.sleep(1000);
			mouseActions.clickElement(driver.findElement(deleteButtonOfOta));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkOtaMasterPagination() {
		// step 1: check the pagination

		// NOTE : you have to update the logic of the pagination firstly
	}

>>>>>>> 83a3fa5fe0e80bce550fd50eae3ca8c088dccca2
	public void selectOtaTypeDropdown() {
		// step 1: select the ota type dropdown
		// step 2: click on the ota type : ALL
		// step 3: validate the pagination and count of ALL ota type
		// step 4: click on the ota type : SET
		// step 5: validate the pagination and count of SET ota type
		// step 6: click on the ota type : GET
		// step 7: validate the pagination and count of GET ota type
		// step 8: click on the ota type : CLR
		// step 9: validate the pagination and count of CLR ota type
<<<<<<< HEAD
		
		// Thank You !!!
	}
	
	// Navigate Back to device-batch page
	
	public void clickCreateNewOtaBatch() {}
	
	public void createManualOtaBatch() {
		/* idea is that run this code in loop thrice, make array of UIN in constants and loop through it 
		 * to have multiple batches Min - 3 
		 */
		
=======

		// Thank You !!!
	}

	// Navigate Back to device-batch page

	public void clickCreateNewOtaBatch() {
	}

	public void createManualOtaBatch() {
		/*
		 * idea is that run this code in loop thrice, make array of UIN in constants and
		 * loop through it to have multiple batches Min - 3
		 */

>>>>>>> 83a3fa5fe0e80bce550fd50eae3ca8c088dccca2
		// step 1 : input batch name - SB_OTA_TEST_MANUAL
		// step 2 : input batch description - SB_OTA_TEST_MANUAL
		// step 3 : select the batch type - Manual OTA
		// step 4 : search the UIN from the UIN array and check the table headings
		// step 5 : enter in input box of search box and press enter
		// step 6 : select the UIN from the table by clicking the checkbox
		// step 7 : try for all UIN from the array
<<<<<<< HEAD
		// step 8 : call    selectOtaCommandsList();
	}
	
	public void selectOtaCommandsList() {
		// step 1 : select the ota type from the dropdown
		// step 2 : match this all ota commands from to the previous function that add that specific ota type 
		//			example if you have added SET ota type then select SET from the dropdown and match all the commands.
		
		// step 3 : select CHTP ota by clicking on the checkbox
		// step 4 : scrol down to the setBatch button
		// step 5 : check the set Batch and select All button is enable 
		// step 6 : click on the set Batch button
		// step 7 : call     setConfigurationValue();
	}
	
	public void setConfigurationValue() {
		// step 1 : check the above selected ota from the list that is exact same as the ota commnand name in the table
        
		/* if the ota command have to input some value in the input box of the input value input box field 
		 * then take the max value from the table and input that value in the input box
         * */
		
		// step 2 : check the action buttons
		// step 3 : scroll to the submit button 
		// step 4 : click on the submit button
		// step 5 : accept the alert
		
		/* it will redirect to the device-batch page then validate the batch name and batch description is same
		 * as that is given in the above function of creation of ota*/
	}
	
	/* After that validate the created at 05 Feb 2025 | 11:55:15 PM in this format.
	 * then validate the batch breakdown and completed percentage and batch status
	 * */
=======
		// step 8 : call selectOtaCommandsList();
	}

	public void selectOtaCommandsList() {
		// step 1 : select the ota type from the dropdown
		// step 2 : match this all ota commands from to the previous function that add
		// that specific ota type
		// example if you have added SET ota type then select SET from the dropdown and
		// match all the commands.

		// step 3 : select CHTP ota by clicking on the checkbox
		// step 4 : scrol down to the setBatch button
		// step 5 : check the set Batch and select All button is enable
		// step 6 : click on the set Batch button
		// step 7 : call setConfigurationValue();
	}

	public void setConfigurationValue() {
		// step 1 : check the above selected ota from the list that is exact same as the
		// ota commnand name in the table

		/*
		 * if the ota command have to input some value in the input box of the input
		 * value input box field then take the max value from the table and input that
		 * value in the input box
		 */

		// step 2 : check the action buttons
		// step 3 : scroll to the submit button
		// step 4 : click on the submit button
		// step 5 : accept the alert

		/*
		 * it will redirect to the device-batch page then validate the batch name and
		 * batch description is same as that is given in the above function of creation
		 * of ota
		 */
	}

	/*
	 * After that validate the created at 05 Feb 2025 | 11:55:15 PM in this format.
	 * then validate the batch breakdown and completed percentage and batch status
	 */
>>>>>>> 83a3fa5fe0e80bce550fd50eae3ca8c088dccca2
}
