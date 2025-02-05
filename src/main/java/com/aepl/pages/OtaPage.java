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
import com.aepl.actions.MouseActions;
import com.aepl.util.CommonMethod;

public class OtaPage {

	// Global variables
	private WebDriver driver;
	private WebDriverWait wait;
	private CommonMethod commonMethod;
	private CalendarActions calendarActions;
	private MouseActions mouseAction;
	private static final Logger logger = LogManager.getLogger(OtaPage.class);

	// Constructor
	public OtaPage(WebDriver driver) {
		this.driver = driver;
		this.commonMethod = new CommonMethod(driver);
		this.calendarActions = new CalendarActions(driver);
		this.mouseAction = new MouseActions(driver);
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
	private By reportButton = By.xpath("//button[@class=\"btn-sm btn example-full-width float-right\"]");

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
		commonMethod.checkTableHeadings(
				Arrays.asList("Batch ID", "Batch Type", "Batch Date", "Total Device Uploaded", "Total Device Completed",
						"Total Device Aborted", "Total Device In-progress/Pending", "Batch Percentage"));
	}

	public boolean checkReportsButtons() {
		List<WebElement> reportButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(reportButton));
		try {
			for (WebElement button : reportButtons) {
				if (button.isEnabled() && button.isDisplayed()) {
					mouseAction.moveToElement(button);
					Thread.sleep(1000);
					mouseAction.clickElement(button);
					return true;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}
}
