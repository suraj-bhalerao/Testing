package com.aepl.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonMethod {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static Logger logger = LogManager.getLogger(CommonMethod.class);

	public CommonMethod(WebDriver driver) {
		CommonMethod.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public static By searchBox = By.name("searchInput");
	private static By tableHeadings = By.xpath("//tr[@class=\"text-center\"]");
	private static By eyeActionButtons = By.xpath("//td[@class = \"ng-star-inserted\"][1]");

	// Screenshot method
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

	// Search box and table heading check method
	public static boolean checkSearchBoxWithTableHeadings(String input, List<String> expectedHeaders) {
		try {
			logger.info("Performing search with input: " + input);

			// Ensure the search box is clickable
			WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
			search.click();
			search.clear();
			search.sendKeys(input);
			search.sendKeys(Keys.ENTER);

			logger.info("Waiting for the table to update...");
			// Get table headers
			List<WebElement> actualHeaderElements = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableHeadings));

			List<String> actualHeaderTexts = actualHeaderElements.stream().map(WebElement::getText).map(String::trim)
					.map(String::toLowerCase).collect(Collectors.toList());

//			actualHeaderTexts
//				.stream()
//				.forEach(s -> System.out.println(s));

			List<String> normalizedExpectedHeaders = expectedHeaders.stream().map(String::trim).map(String::toLowerCase)
					.collect(Collectors.toList());
//			
//			normalizedExpectedHeaders
//				.stream()
//				.forEach(s -> System.out.println(s));

			logger.info("Actual table headers after search: " + actualHeaderTexts);
			logger.info("Expected table headers: " + normalizedExpectedHeaders);

			boolean headersMatch = actualHeaderTexts.equals(normalizedExpectedHeaders);

			if (!headersMatch) {
				logger.error("Table headers do not match!");
			}

			return headersMatch;
		} catch (Exception e) {
			logger.error("Exception during search or validation process", e);
			throw new RuntimeException("Validation failed due to an exception", e);
		}
	}

	// Clicking on the eye button
	public static void clickEyeActionButton() {
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

	// Goes to new tab method
	public static String switchToTabByIndex(WebDriver driver, int tabIndex) {
		String originalTab = driver.getWindowHandle();
		logger.info("Original tab handle stored: " + originalTab);

		Set<String> allTabs = driver.getWindowHandles();
		ArrayList<String> tabList = new ArrayList<>(allTabs);

		if (tabIndex < 0 || tabIndex >= tabList.size()) {
			logger.error("Invalid tab index: " + tabIndex + ". Total tabs open: " + tabList.size());
			throw new RuntimeException("Invalid tab index: " + tabIndex);
		}

		String targetTab = tabList.get(tabIndex);
		logger.info("Switching to tab with index: " + tabIndex + ", handle: " + targetTab);
		driver.switchTo().window(targetTab);

		return originalTab;
	}

	// Tab back to normal
	public static void switchBackToOriginalTab(WebDriver driver, String originalTab) {
		logger.info("Switching back to the original tab: " + originalTab);
		driver.switchTo().window(originalTab);
	}

	// Pagination method
	public static void checkPagination(By nextButton, By previousButton, By activeBtn) {
		logger.info("Starting pagination validation with scrolling and delay.");

		try {
			for (int i = 1; i <= 5; i++) {
				wait.until(ExpectedConditions.textToBePresentInElementLocated(activeBtn, String.valueOf(i)));
				logger.info("Successfully navigated to page: " + i);

				if (i < 5) {
					WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", next);
					Thread.sleep(500); 
					next.click();
					logger.info("Clicked on the 'Next' button to navigate to page " + (i + 1));
				}
			}

			for (int i = 4; i >= 1; i--) {
				WebElement previous = wait.until(ExpectedConditions.elementToBeClickable(previousButton));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", previous);
				Thread.sleep(500); 
				previous.click();
				logger.info("Clicked on the 'Previous' button to navigate back to page " + i);

				wait.until(ExpectedConditions.textToBePresentInElementLocated(activeBtn, String.valueOf(i)));
			}

			logger.info("Pagination validation completed successfully.");
		} catch (Exception e) {
			logger.error("An error occurred during pagination validation.", e);
			throw new RuntimeException("Pagination validation failed due to an exception.", e);
		}
	}

}
