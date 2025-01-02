package com.aepl.actions;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarActions {
	private WebDriver driver;
	private WebDriverWait wait;

	public CalendarActions(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}
	
	public void selectDate(By calendarLocator, String targetDate) {
		// Open the calendar widget
		WebElement calendarElement = wait.until(ExpectedConditions.elementToBeClickable(calendarLocator));
		calendarElement.click();

		// Parse the date into day, month, and year
		String[] dateParts = targetDate.split("-");
		String day = dateParts[0];
		String month = dateParts[1];
		String year = dateParts[2];

		// Navigate to the correct year and month
		while (true) {
			String displayedMonthYear = driver.findElement(By.cssSelector("selector-for-month-year")).getText();
			if (displayedMonthYear.contains(month) && displayedMonthYear.contains(year)) {
				break;
			}
			driver.findElement(By.cssSelector("selector-for-next-month")).click(); // Navigate to the next month
		}

		// Select the day
		By dayLocator = By.xpath("//td[text()='" + day + "']");
		WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
		dayElement.click();
	}
	
	public void demo () {
		System.out.println("Hello");
	}
}
