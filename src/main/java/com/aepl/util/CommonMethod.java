package com.aepl.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CommonMethod {

	private static WebDriver driver = WebDriverFactory.getWebDriver(ConfigProperties.getProperty("browser.type"));
	private static Logger logger = LogManager.getLogger(CommonMethod.class);

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
	
	public static void switchBackToOriginalTab(WebDriver driver, String originalTab) {
        logger.info("Switching back to the original tab: " + originalTab);
        driver.switchTo().window(originalTab);
    }
}
