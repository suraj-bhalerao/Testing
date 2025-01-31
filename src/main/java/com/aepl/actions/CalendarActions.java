package com.aepl.actions;

import java.time.Duration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarActions {
	private final WebDriver driver;
	private final WebDriverWait wait;

	private static final Logger logger = LogManager.getLogger(CalendarActions.class);

	public CalendarActions(WebDriver driver) {
	    if (driver == null) {
	        throw new IllegalArgumentException("WebDriver instance cannot be null");
	    }
	    this.driver = driver;
	    this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}
	
	public void selectDate(By calendarLocator, String targetDate) {
	    if (calendarLocator == null || targetDate == null || targetDate.isEmpty()) {
	        logger.error("Invalid calendarLocator or targetDate provided. Both must be non-null and targetDate must be non-empty.");
	        throw new IllegalArgumentException("calendarLocator and targetDate cannot be null or empty");
	    }

	    logger.info("Attempting to select date: " + targetDate);

	    try {
	        logger.info("Waiting for the calendar element to become clickable...");
	        WebElement calendarElement = wait.until(ExpectedConditions.elementToBeClickable(calendarLocator));
	        logger.info("Calendar element found. Clicking to open the date picker.");
	        calendarElement.click();

	        logger.info("Parsing target date: " + targetDate);
	        String[] dateParts = targetDate.split("-");
	        String day = dateParts[0];
	        String month = dateParts[1];
	        String year = dateParts[2];
	        logger.info("Parsed date: Day=" + day + ", Month=" + month + ", Year=" + year);

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

	        By dayLocator = By.xpath("//td[text()='" + day + "']");
	        logger.info("Waiting for the target day (" + day + ") to become clickable...");
	        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
	        logger.info("Target day found. Clicking to select.");
	        dayElement.click();

	        logger.info("Date selection completed successfully: " + targetDate);

	    } catch (Exception e) {
	        logger.error("Error occurred while selecting date: " + targetDate, e);
	        throw e; 
	    }
	}
  

}

