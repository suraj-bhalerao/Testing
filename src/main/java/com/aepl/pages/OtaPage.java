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

import com.aepl.util.CommonMethod;

public class OtaPage {

	// Global variables
	private final WebDriver driver;
	private final WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(OtaPage.class);

	// Constructor
	public OtaPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Locators goes here
	private final By navBarLink = By.xpath("//span[@class=\"headers_custom color_3D5772\"]");
	private final By ota = By.xpath("//a[@class=\"dropdown-item ng-star-inserted\"][4]");
	private final By ButtonsList = By.xpath("//button[@class=\"btn btn-outline-primary ng-star-inserted\"]");
	private final By searchBox = By.xpath("//input[@placeholder=\"Search and Press Enter\"]");
	private final By tableHeadings = By.xpath("//tr[@class=\"text-center\"]");

	// Methods goes here
	public void clickNavBar() {
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navBarLink));

		boolean isClicked = false;
		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Device Utility")) {
				link.click();
				isClicked = true;
				break;
			}
		}
		if (!isClicked) {
			throw new RuntimeException("Failed to find and click on 'Device Utility' in the navigation bar.");
		}
	}

	public String clickDropdown() {
		try {
			WebElement changeMobileLink = wait.until(ExpectedConditions.visibilityOfElementLocated(ota));
			changeMobileLink.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Change Mobile option.", e);
			throw new RuntimeException("Failed to click on Change Mobile option", e);
		}
	}

	public void checkButtons() {
		try {
			List<WebElement> btnList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ButtonsList));
			logger.info("Found " + btnList.size() + " buttons on the page.");

			for (int i = 0; i < btnList.size(); i++) {
				WebElement btn = btnList.get(i);

				if (btn.isDisplayed() && btn.isEnabled()) {
					logger.info("Clicking button " + (i + 1) + " with text: " + btn.getText());

					btn.click();

					logger.info("Navigated to URL: " + driver.getCurrentUrl());

					driver.navigate().back();
					logger.info("Navigated back to the original page.");

					btnList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ButtonsList));
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

	public boolean checkSearchBoxAndTable(String batchID, List<String> expectedHeaders) {
		logger.info("Taking table heading before the search");
		boolean isTableMatch = CommonMethod.checkSearchBoxWithTableHeadings(batchID, expectedHeaders);
		return isTableMatch;
	}

	public void checkPagination() {
	}

	public void checkActionButtons() {
	}

	public void checkOtaBatchReportButton() {
	}

	public void checkOtaMasterButton() {
	}

	public void checkCreateNewBatchButton() {
	}

	// Helping methods goes here

}
