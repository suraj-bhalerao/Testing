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

	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(CommonMethod.class);

	public CommonMethod(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public By searchBox = By.xpath("//*[@placeholder='Search and Press Enter']");
	private By tableHeadings = By.xpath("//table[@id='DataTables_Table_0']//th");
	private By eyeActionButtons = By.xpath("//mat-icon[contains(text(),'visibility')]");

	public void captureScreenshot(String testCaseName) {
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

	public boolean checkSearchBoxWithTableHeadings(String input, List<String> expectedHeaders) {
		try {
			logger.info("Performing search with input: " + input);

			WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
			search.click();
			search.clear();
			search.sendKeys(input);
			search.sendKeys(Keys.ENTER);

			logger.info("Waiting for the table to update...");
			Thread.sleep(2000);
//			search.clear();
//			Thread.sleep(2000);
//			search.sendKeys(Keys.ENTER);
//			Thread.sleep(2000);
//			search.sendKeys(Keys.TAB);
//			Thread.sleep(2000);

			List<WebElement> actualHeaderElements = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableHeadings));
			
			List<String> actualHeaderTexts = actualHeaderElements
					.stream()
					.map(WebElement::getText)
					.map(String::trim)
					.map(String::toLowerCase)
//					.peek(header -> System.out.println("Actual Header: " + header)) 																				// headers
					.collect(Collectors.toList());

			List<String> normalizedExpectedHeaders = expectedHeaders
					.stream()
					.map(String::trim)
					.map(String::toLowerCase)
//					.peek(header -> System.out.println("Expected Header: " + header)) 
					.collect(Collectors.toList());

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

	public void clickEyeActionButton() {
		logger.info("Locating the eye action button...");
		try {
			WebElement eyeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(eyeActionButtons));
			logger.info("Eye action button located. Clicking on it.");
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", eyeButton);
			
			Thread.sleep(1000);
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

	public String switchToTabByIndex(WebDriver driver, int tabIndex) {
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

	public void switchBackToOriginalTab(WebDriver driver, String originalTab) {
		logger.info("Switching back to the original tab: " + originalTab);
		driver.switchTo().window(originalTab);
	}

	// Below two methods are for pagination check
	public void checkPagination(WebElement nextButton, WebElement previousButton, WebElement activeBtn) {
	    logger.info("Starting pagination validation with scrolling and delay.");

	    try {
	        for (int i = 1; i <= 5; i++) {
	            if (!navigateToPage(i, nextButton, activeBtn)) break; 
	        }

	        for (int i = 4; i >= 1; i--) {
	            if (!navigateToPage(i, previousButton, activeBtn)) break; 
	        }

	        logger.info("Pagination validation completed successfully.");
	    } catch (Exception e) {
	        logger.error("An error occurred during pagination validation.", e);
	        throw new RuntimeException("Pagination validation failed due to an exception.", e);
	    }
	}

	private boolean navigateToPage(int pageNumber, WebElement buttonLocator, WebElement activeBtn) {
	    try {     
	        
	        logger.info("Successfully navigated to page: " + pageNumber);

	        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));

	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
	        
	        if (!button.isEnabled()) {
	            logger.info("Button is disabled, stopping pagination.");
	            return false;
	        }

	        button.click();
	        logger.info("Clicked on the button to navigate to the next page.");
	        
	        return true;
	    } catch (Exception e) {
	        logger.error("Failed to navigate to page " + pageNumber, e);
	        return false; 
	    }
	}
}
