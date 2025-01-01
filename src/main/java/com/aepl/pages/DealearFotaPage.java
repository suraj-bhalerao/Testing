package com.aepl.pages;

import java.time.Duration;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DealearFotaPage {
	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(DealearFotaPage.class);

	public DealearFotaPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		logger.info("Initialized the driver and wait ");
	}

	// Locators goes here
	private By navBarLink = By.xpath("//span[@class=\"headers_custom color_3D5772\"]");

	
	
	// Methods goes here
	public void clickNavBar() {
		logger.info("Trying to clicking on the navigation bar");
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navBarLink));

		boolean isClicked = false;
		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Dealer FOTA")) {
				link.click();
				logger.info("Clicked on dealer fota");
				isClicked = true;
				break;
			}
		}
		if (!isClicked) {
			logger.info("Error is happened");
			throw new RuntimeException("Failed to find and click on 'Device Utility' in the navigation bar.");
		}
	}
	
	public void clickAddApprovedFileButton() {
		logger.info("Clicking on the 'Add Approved File' Button");
		
	}
}
