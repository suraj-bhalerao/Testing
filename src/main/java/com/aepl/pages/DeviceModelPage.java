package com.aepl.pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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
	// Global variables
	private final WebDriver driver;
	private final WebDriverWait wait;
	private CommonMethod commonMethod;
	private final Logger logger = LogManager.getLogger(DeviceModelPage.class);
	
	// Locators
	private final By ModelNameInput = By.xpath("//input[@formcontrolname='modelName']");
	private final By ModelCodeInput = By.xpath("//input[@formcontrolname='modelCode']");
	private final By HWVersionInput = By.xpath("//input[@formcontrolname='hwVersion']");
	private final By SubmitButton = By.xpath("//button[contains(.,'Submit')]");
	private final By dropDown = By.xpath("//span[@class='headers_custom color_3D5772']");
	private final By DeviceModel = By.xpath("//a[@routerlink='model']");
	private final By AddDeviceModel = By.xpath("//button[contains(.,'Add Device Model')]");
	private final By pageHeader = By.xpath("//h4[@class='h4ssssss text-cente mt-1']");

	// Constructor
	public DeviceModelPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.commonMethod = new CommonMethod(driver);	
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

	public boolean CheckPageHeader(List<String> expectedHeaders) {
		try {
			List<WebElement> actualHeaders = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pageHeader));

			logger.info("Taking table heading after the search");
			List<String> actualHeaderTexts = actualHeaders.stream().map(WebElement::getText)
					.collect(Collectors.toList());

			return actualHeaderTexts.equals(expectedHeaders);
		} catch (Exception e) {
			logger.error("Error during search or header validation", e);
			throw new RuntimeException("Search or validation failed", e);
		}
	}

	public boolean AddNewDeviceModel() {
		logger.info("Trying to add the new model");

		try {
			WebElement ModelName = wait.until(ExpectedConditions.visibilityOfElementLocated(ModelNameInput));
			WebElement ModelCode = wait.until(ExpectedConditions.visibilityOfElementLocated(ModelCodeInput));
			WebElement HWVersion = wait.until(ExpectedConditions.visibilityOfElementLocated(HWVersionInput));
			WebElement SubmitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(SubmitButton));

			String ModelNameInput = commonMethod.randomStringGen();
			String ModelCodeInput = commonMethod.randomStringGen();
			String HWVersionInput = commonMethod.randomStringGen();

			ModelName.click();
			ModelName.sendKeys(ModelNameInput);
			ModelName.sendKeys(Keys.TAB);

			ModelCode.sendKeys(ModelCodeInput);
			ModelCode.sendKeys(Keys.TAB);

			HWVersion.sendKeys(HWVersionInput);

			logger.info("Clicking on the save file button");
			SubmitBtn.click();
		} catch (Exception e) {
			logger.error("An error occurred while adding or validating the file", e);
			throw new RuntimeException("File addition or validation failed", e);
		}

		logger.error("File validation failed. File name or date-time not found in the table");
		return false;
	}

}
