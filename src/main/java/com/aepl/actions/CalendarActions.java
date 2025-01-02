package com.aepl.actions;

import java.time.Duration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Suraj_Bhalerao
 * 
 * 
 * The CalendarActions class provides utility methods to interact with calendar components on web pages.
 * It allows for the selection of specific dates by navigating through the calendar and choosing the target day.
 * 
 * Features:
 * - Waits for the calendar element to become clickable before interacting with it.
 * - Navigates through months and years to locate the target date.
 * - Selects the specified day from the calendar.
 * 
 * Dependencies:
 * - Selenium WebDriver for browser automation.
 * - WebDriverWait for handling dynamic waits.
 * 
 * Logging:
 * - Uses Apache Log4j for logging actions and errors.
 */

public class CalendarActions {
	private WebDriver driver;
	private WebDriverWait wait;

	private static final Logger logger = LogManager.getLogger(CalendarActions.class);

	/**
	 * Constructs a CalendarActions instance with the specified WebDriver.
	 *
	 * @param driver the WebDriver instance used to control the browser.
	 * @throws IllegalArgumentException if the WebDriver instance is null.
	 */
	public CalendarActions(WebDriver driver) {
	    if (driver == null) {
	        throw new IllegalArgumentException("WebDriver instance cannot be null");
	    }
	    this.driver = driver;
	    this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	/**
	 * Selects a specified date from a calendar widget on the web page.
	 *
	 * @param calendarLocator the By locator for the calendar element.
	 * @param targetDate the target date to select, in the format "dd-MM-yyyy" (e.g., "15-08-2025").
	 * @throws IllegalArgumentException if the targetDate format is invalid.
	 * @throws NoSuchElementException if the calendar elements are not found.
	 * @throws TimeoutException if the calendar or day element is not interactable within the specified wait time.
	 * 
	 * How It Works:
	 * 1. Clicks on the calendar element to open the date picker.
	 * 2. Splits the target date into day, month, and year components.
	 * 3. Navigates through months and years until the target date's month and year are displayed.
	 * 4. Selects the specified day from the calendar.
	 */

	public void selectDate(By calendarLocator, String targetDate) {
	    if (calendarLocator == null || targetDate == null || targetDate.isEmpty()) {
	        logger.error("Invalid calendarLocator or targetDate provided. Both must be non-null and targetDate must be non-empty.");
	        throw new IllegalArgumentException("calendarLocator and targetDate cannot be null or empty");
	    }

	    logger.info("Attempting to select date: " + targetDate);

	    try {
	        // Wait for the calendar element to be clickable and click it
	        logger.info("Waiting for the calendar element to become clickable...");
	        WebElement calendarElement = wait.until(ExpectedConditions.elementToBeClickable(calendarLocator));
	        logger.info("Calendar element found. Clicking to open the date picker.");
	        calendarElement.click();

	        // Parse the target date
	        logger.info("Parsing target date: " + targetDate);
	        String[] dateParts = targetDate.split("-");
	        String day = dateParts[0];
	        String month = dateParts[1];
	        String year = dateParts[2];
	        logger.info("Parsed date: Day=" + day + ", Month=" + month + ", Year=" + year);

	        // Navigate through the calendar to find the correct month and year
	        while (true) {
	            logger.info("Checking displayed month and year...");
	            WebElement displayedMonthYearElement = driver.findElement(By.cssSelector("selector-for-month-year"));
	            String displayedMonthYear = displayedMonthYearElement.getText();
	            logger.info("Displayed month and year: " + displayedMonthYear);

	            if (displayedMonthYear.contains(month) && displayedMonthYear.contains(year)) {
	                logger.info("Target month and year found: " + displayedMonthYear);
	                break;
	            }

	            logger.info("Target month and year not found. Clicking next to navigate...");
	            driver.findElement(By.cssSelector("selector-for-next-month")).click();
	        }

	        // Select the target day
	        By dayLocator = By.xpath("//td[text()='" + day + "']");
	        logger.info("Waiting for the target day (" + day + ") to become clickable...");
	        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
	        logger.info("Target day found. Clicking to select.");
	        dayElement.click();

	        logger.info("Date selection completed successfully: " + targetDate);

	    } catch (Exception e) {
	        logger.error("Error occurred while selecting date: " + targetDate, e);
	        throw e; // Re-throw exception for upstream handling
	    }
	}

}
