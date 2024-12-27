package com.aepl.pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeviceModelPage {
	// drivers and Logger
	private WebDriver driver;
	private static final Logger logger = LogManager.getLogger(DeviceModelPage.class);

	// Constructor
	public DeviceModelPage(WebDriver driver) {
		super();
		logger.info("Launching the constructor of 'Device Model Page...'");
		this.driver = driver;
	}

	// Selectors goes here
	public By dropDownList = By.xpath("//a[@class='nav-link']");

	// Supportive methods goes here
	public WebElement waitForVisibility(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public List<WebElement> waitForVisibilityOfLocators(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
}
