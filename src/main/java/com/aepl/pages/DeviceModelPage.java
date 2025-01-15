package com.aepl.pages;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aepl.util.CommonMethod;

public class DeviceModelPage {
	private WebDriver driver;
	private WebDriverWait wait;
	
	private Logger logger = LogManager.getLogger(DeviceModelPage.class);
	private By ModelNameInput = By.xpath("//div[@class='mat-form-field-infix ng-tns-c68-1']");
	private By ModelCodeInput = By.xpath("//div[@class='mat-form-field-infix ng-tns-c68-2']");
	private By HWVersionInput = By.xpath("//div[@class='mat-form-field-infix ng-tns-c68-3']");
	private By SubmitButton = By.xpath("//button[contains(.,'Submit')]");
	private By ToastLocator = By.xpath("//span[text()='Fetched Successfully']");
	private By dropDown = By.xpath("//span[@class='headers_custom color_3D5772']");
	private By DeviceModel = By.xpath("//a[@routerlink='model']");
	private By AddDeviceModel = By.xpath("//button[contains(.,'Add Device Model')]");

	// Constructor
	public DeviceModelPage(WebDriver driver) {
		super();
		logger.info("Launching the constructor of 'Device Model Page...'");
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Methods Goes here
	public void clickNavBar() {
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropDown));

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

	public String clickDeviceModel() {
		// Click on the element 'Device Model' and return the current URL
		try {
			WebElement DevicemodelLink = wait.until(ExpectedConditions.visibilityOfElementLocated(DeviceModel));
			DevicemodelLink.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Device Model option.", e);
			throw new RuntimeException("Failed to click on Device Model option", e);
		}
	}

	public String clickAddDeviceModel() {
		// Click on the element 'Add Device Model' and return the current URL
		try {
			WebElement DevicemodelLink = wait.until(ExpectedConditions.visibilityOfElementLocated(AddDeviceModel));
			DevicemodelLink.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Add Device Model button.", e);
			throw new RuntimeException("Failed to click on Add Device Model button", e);
		}
	}

	
	
	
	
	
	
	
	
	
	public boolean AddNewDeviceModel() {
		logger.info("Trying to add the new model");

		try {
			WebElement ModelName = wait.until(ExpectedConditions.visibilityOfElementLocated(ModelNameInput));
			WebElement ModelCode = wait.until(ExpectedConditions.visibilityOfElementLocated(ModelCodeInput));
			WebElement HWVersion = wait.until(ExpectedConditions.visibilityOfElementLocated(HWVersionInput));
			WebElement SubmitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(SubmitButton));

			String ModelNameInput = "asdkjfgaskjd";
			String ModelCodeInput = "ajklsdhfasdjk";
			String HWVersionInput = "jklahsdjk";

			ModelName.click();
			ModelName.sendKeys(ModelNameInput);
			ModelName.sendKeys(Keys.TAB);

			ModelCode.sendKeys(ModelCodeInput);
			ModelCode.sendKeys(Keys.TAB);

			HWVersion.sendKeys(HWVersionInput);

			logger.info("Clicking on the save file button");
			SubmitBtn.click();

//			// Validate Toast Message
//			WebElement toastElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ToastLocator));
//			String actualToastMessage = toastElement.getText(); // Ensure toastLocator is correct
//			logger.info("Captured toast message: " + actualToastMessage);
//
//			if (!expectedToastMessage.equals(actualToastMessage)) {
//				logger.error("Expected toast message: " + expectedToastMessage + ", but found: " + actualToastMessage);
//				throw new RuntimeException("Toast message validation failed");
//			}
//
//			// Validate File in the Table
//			Thread.sleep(2000); // Replace with explicit waits if possible
//			/*
//			 * By tableRowsLocator; List<WebElement> tableRows =
//			 * driver.findElements(tableRowsLocator); for (WebElement row : tableRows) {
//			 * List<WebElement> cells = row.findElements(By.tagName("td")); if (cells.size()
//			 * > 0) { String rowFileName = cells.get(1).getText(); String rowDateTime =
//			 * cells.get(2).getText(); logger.info("File Name: " + rowFileName +
//			 * ", Date Time: " + rowDateTime);
//			 * 
//			 * if (fileName.equals(rowFileName) && capturedDateTime.equals(rowDateTime)) {
//			 * logger.info("File and date-time validated successfully in the table"); return
//			 * true;
//			 */
//			// }
//			// }
//			// }
		} catch (Exception e) {
			logger.error("An error occurred while adding or validating the file", e);
			throw new RuntimeException("File addition or validation failed", e);
		}

		logger.error("File validation failed. File name or date-time not found in the table");
		return false;
	}

}
