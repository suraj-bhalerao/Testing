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

import com.aepl.actions.HoverActions;
import com.aepl.util.CommonMethod;

public class OtaPage {

	// Global variables
	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(OtaPage.class);
	private CommonMethod commonMethod;
	private HoverActions hover;
	
	// Constructor
	public OtaPage(WebDriver driver) {
		this.driver = driver;
		this.commonMethod = new CommonMethod(driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.hover = new HoverActions(driver);
	}

	// Locators
	private By navBarLink = By.xpath("//span[@class='headers_custom color_3D5772']");
	private By otaLink = By.xpath("//a[@class='dropdown-item ng-star-inserted'][4]");
	private By buttonsList = By.xpath("//button[@class='btn btn-outline-primary ng-star-inserted']");

	private By nextBtn = By.xpath("//a[@class=\"ng-star-inserted\"]");
	private By prevBtn = By.xpath("//a[@class=\"ng-star-inserted\"]");
	private By activeBtn = By.xpath("//a[@class=\"ng-star-inserted\"]");
	
	public By eyeActionButton = By.xpath("//tbody/tr[1]/td[9]/mat-icon[1]");
	
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
		
		logger.log(Level.INFO , "Taking table heading before the search");
		return commonMethod.checkSearchBoxWithTableHeadings(batchName, expectedHeaders);
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

	public void checkActionButtons() {
		hover.hoverOverElement(wait.until(ExpectedConditions.presenceOfElementLocated(eyeActionButton)));
		commonMethod.clickEyeActionButton();
//		commonMethod.switchToTabByIndex(driver, 1);
//		commonMethod.switchBackToOriginalTab(driver, "0");
	}
}
