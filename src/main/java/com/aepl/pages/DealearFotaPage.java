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
	private By navBarLink = By.xpath("//*[@id=\"navbarDropdownProfile\"]/span");
	private By dealerFota = By.xpath("//a[@class=\"dropdown-item ng-star-inserted\"][6]");
	private By addApprovedFileBtn = By.xpath("/html/body/app-root/app-dealer-fota/div/div/div[2]/button");
	
	
	// Methods goes here
	public void clickNavBar() {
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navBarLink));

		boolean isClicked = false;
		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Device Utility")) {
				link.click();
				isClicked = true;
				clickDropDownOption();
				break;
			}
		}
		if (!isClicked) {
			throw new RuntimeException("Failed to find and click on 'Device Utility' in the navigation bar.");
		}
	}
	
	public String clickDropDownOption() {
		try {
			WebElement dealerFotaLink = wait.until(ExpectedConditions.visibilityOfElementLocated(dealerFota));
			dealerFotaLink.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Change Mobile option.", e);
			throw new RuntimeException("Failed to click on Change Mobile option", e);
		}
	}
	
	public void clickAddApprovedFileButton() {
		logger.info("Clicking on the 'Add Approved File' Button");
		WebElement fileBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(addApprovedFileBtn));
		
		if(fileBtn.isEnabled() && fileBtn.isDisplayed()) {
			logger.info("Add Approved File buttton is visible and clickable");
			fileBtn.click();
		}
	}
}
