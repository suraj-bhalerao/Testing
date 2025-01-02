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
	private WebDriver driver;
	private WebDriverWait wait;

	private static final Logger logger = LogManager.getLogger(CalendarActions.class);

	public CalendarActions(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	public void selectDate(By calendarLocator, String targetDate) {
		WebElement calendarElement = wait.until(ExpectedConditions.elementToBeClickable(calendarLocator));
		calendarElement.click();

		String[] dateParts = targetDate.split("-");
		String day = dateParts[0];
		String month = dateParts[1];
		String year = dateParts[2];

		while (true) {
			String displayedMonthYear = driver.findElement(By.cssSelector("selector-for-month-year")).getText();
			if (displayedMonthYear.contains(month) && displayedMonthYear.contains(year)) {
				break;
			}
			driver.findElement(By.cssSelector("selector-for-next-month")).click(); // Navigate to the next month
		}

		By dayLocator = By.xpath("//td[text()='" + day + "']");
		WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
		dayElement.click();
	}
}
