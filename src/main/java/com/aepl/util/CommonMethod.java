package com.aepl.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aepl.constants.Constants;

public class CommonMethod {

	private static WebDriver driver = WebDriverFactory.getWebDriver(ConfigProperties.getProperty("browser.type"));
	private static Logger logger = LogManager.getLogger(CommonMethod.class);
	
	public static By searchBox = By.xpath("//input[@placeholder=\"Search and Press Enter\"]");
	private By tableHeadings = By.xpath("//tr[@class=\"text-center\"]");
	private By eyeActionButtons = By.xpath("//td[@class = \"ng-star-inserted\"][1]");
	private WebDriverWait wait;
	
	// Screenshot logic
	public static void captureScreenshot(String testCaseName) {
		if (driver == null) {
			logger.error("Driver is null, unable to capture screenshot.");
			return;
		}

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String screenshotName = testCaseName + "_" + timestamp + ".png";

		String screenshotPath = "screenshots/" + screenshotName;

		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(screenshotPath));
			logger.info("Screenshot captured: " + screenshotPath);
		} catch (IOException e) {
			logger.error("Failed to capture screenshot for test case: " + testCaseName, e);
		}
	}

	
	public boolean checkSearchBox(String iccid, List<String> expectedHeaders) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			WebElement search = 
					wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));

			logger.info("Taking table heading before the search");
			List<WebElement> actualHeaders = 
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableHeadings));

			logger.info("Trying to clicking on search box and search something");
			search.click();
			search.clear();
			search.sendKeys(iccid);
			search.sendKeys(Keys.ENTER);

			logger.info("Taking table heading after the search");
			List<String> actualHeaderTexts = actualHeaders.stream().map(WebElement::getText)
					.collect(Collectors.toList());

			return actualHeaderTexts.equals(expectedHeaders) ? true : false;
		} catch (Exception e) {
			logger.error("Error during search or header validation", e);
			throw new RuntimeException("Search or validation failed", e);
		}
	}
	
	public void clickEyeActionButton() {
		logger.info("Locating the eye action button...");
		try {
			WebElement eyeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(eyeActionButtons));
			logger.info("Eye action button located. Clicking on it.");
			eyeButton.click();
			logger.info("Page validation successful. Navigating back.");
			driver.navigate().back();
			wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
			logger.info("Navigated back to the original page.");
		} catch (Exception e) {
			logger.error("An error occurred while interacting with the eye action button.", e);
			throw new RuntimeException("Failed to process the eye action button.", e);
		}
	}
	
	
	
	
		
		
		
	



	// Random String Generator
	public static String randomStringGen() {
		int length = 10; 
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder randomString = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			randomString.append(characters.charAt(index));
		}
		return randomString.toString();
	}
}

