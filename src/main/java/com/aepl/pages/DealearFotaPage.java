package com.aepl.pages;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.random.RandomGenerator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aepl.util.CommonMethod;

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
	private By fileNameInput = By.tagName("input");
	private By saveFileButton = By.xpath("//button[@class='btn btn-primary w-100']");
	private By tableRowsLocator = By.xpath("//tr[@class=\"odd text-center ng-star-inserted\"]");
	private By toastLocator = By.id("cdk-overlay-1");
	
	
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

		if (fileBtn.isEnabled() && fileBtn.isDisplayed()) {
			logger.info("Add Approved File buttton is visible and clickable");
			fileBtn.click();
			logger.info("Add Approved File button");
		}
	}

	public boolean addNewFileAndValidate(String expectedToastMessage) {
	    logger.info("Trying to add the new file");

	    // File Adding
	    try {
	        WebElement inputBox = wait.until(ExpectedConditions.visibilityOfElementLocated(fileNameInput));
	        WebElement fileBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(saveFileButton));

	        String fileName = CommonMethod.randomStringGen();
	        inputBox.sendKeys(Keys.ENTER);
	        inputBox.sendKeys(fileName);

	        String dateTimeFormat = "MM/dd/yyyy, hh:mm:ss a";
	        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
	        String capturedDateTime = formatter.format(new Date());

	        logger.info("Clicking on the save file button");
	        fileBtn.click();

	        // Capture toast message
	        WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator)); // Replace `toastLocator` with the actual toast locator
	        String actualToastMessage = toastElement.getText();
	        logger.info("Captured toast message: " + actualToastMessage);

	        // Validate toast message
	        if (!expectedToastMessage.equals(actualToastMessage)) {
	            logger.error("Expected toast message: " + expectedToastMessage + ", but found: " + actualToastMessage);
	            throw new RuntimeException("Toast message validation failed");
	        }

	        // Validate file in the table
	        Thread.sleep(2000); // Adjust as per application behavior
	        List<WebElement> tableRows = driver.findElements(tableRowsLocator); // Replace `tableRowsLocator` with the table row locator
	        for (WebElement row : tableRows) {
	            List<WebElement> cells = row.findElements(By.tagName("td"));
	            if (cells.size() > 0) {
	                String rowFileName = cells.get(1).getText();
	                String rowDateTime = cells.get(2).getText();
	                logger.info("File Name: " + rowFileName + ", Date Time: " + rowDateTime);

	                if (fileName.equals(rowFileName) && capturedDateTime.equals(rowDateTime)) {
	                    logger.info("File and date-time validated successfully in the table");
	                    return true;
	                }
	            }
	        }
	    } catch (Exception e) {
	        logger.error("An error occurred while adding or validating the file", e);
	        throw new RuntimeException("File addition or validation failed", e);
	    }

	    logger.error("File validation failed. File name or date-time not found in the table");
	    return false;
	}

}
