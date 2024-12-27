package com.aepl.pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangeMobilePage {
	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(ChangeMobilePage.class);

	public ChangeMobilePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Locators Goes here
	private By navBarLink = By.xpath("//span[@class=\"headers_custom color_3D5772\"]");
	private By changeMobile = By.xpath("//a[@class=\"dropdown-item ng-star-inserted\"][8]");
	private By searchBox = By.xpath("//input[@placeholder=\"Search and Press Enter\"]");
	private By tableHeadings = By.xpath("//tr[@class=\"text-center\"]");
	private By eyeActionButtons = By.xpath("//td[@class = \"ng-star-inserted\"][1]");
	private By deleteActionButtons = By.xpath("//td[@class = \"ng-star-inserted\"][2]");
	private By paginationNextButton = By.xpath("//li[@class=\"pagination-next ng-star-inserted\"");
	private By paginationPreviousButton = By.xpath("//li[@class=\"pagination-previous ng-star-inserted\"]");
	private By pageHeader = By.cssSelector("h4.h4ssssss.text-cente.mt-1");
	
	// Methods Goes here
	public void clickNavBar() {
		// Wait for the navigation bar links to be visible
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navBarLink));

		boolean isClicked = false;
		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Device Utility")) {
				link.click();
//				System.out.println("Clicked On Element On Nav: " +link.getAccessibleName());
				isClicked = true;
				break;
			}
		}

		if (!isClicked) {
			throw new RuntimeException("Failed to find and click on 'Device Utility' in the navigation bar.");
		}
	}

	public String clickDropDownOption() {
		// Click on the element 'changeMobile' and return the current URL
		try {
			WebElement changeMobileLink = wait.until(ExpectedConditions.visibilityOfElementLocated(changeMobile));
			changeMobileLink.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Change Mobile option.", e);
			throw new RuntimeException("Failed to click on Change Mobile option", e);
		}
	}

	public boolean checkSearchBox(String iccid, List<String> expectedHeaders) {
		try {
			WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
			search.click();
			search.clear();
			search.sendKeys(iccid);
			search.sendKeys(Keys.ENTER);

			// Validate table headers after search
			List<WebElement> actualHeaders = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableHeadings));
			List<String> actualHeaderTexts = actualHeaders.stream().map(WebElement::getText)
					.collect(Collectors.toList());

			return actualHeaderTexts.equals(expectedHeaders);
		} catch (Exception e) {
			logger.error("Error during search or header validation", e);
			throw new RuntimeException("Search or validation failed", e);
		}
	}
	
/*
	 TODO : Later think on this
		public void checkTableHeading() {
			// Check table heading is as expected here and validate
		}
*/
	public void clickEyeActionButton() {
		logger.info("Locating the eye action button...");
		try {
			WebElement eyeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(eyeActionButtons));
			logger.info("Eye action button located. Clicking on it.");
			eyeButton.click();
			logger.info("Performing actions on the navigated page...");
			Thread.sleep(2000);
			driver.navigate().back();
			logger.info("Navigated back to the original page.");
		} catch (Exception e) {
			logger.error("An error occurred while interacting with the eye action button.", e);
			throw new RuntimeException("Failed to process the eye action button.", e);
		}
	}

	public void clickDeleteActionButton() {
		// Click on delete button, validate alert and come back
	}

	public void checkPagination() {
		// Click on pagination next and previous buttons and validate it
		// NOTE : First click next button and then previous.,
	}
}
