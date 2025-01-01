package com.aepl.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CommonMethod {

	private static WebDriver driver = WebDriverFactory.getWebDriver(ConfigProperties.getProperty("browser.type"));

	public static void captureScreenshot(String testCaseName) {

		if (driver == null) {
			System.err.println("Driver is null, unable to capture screenshot.");
			return;
		}

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String screenshotName = testCaseName + "_" + timestamp + ".png";

		String screenshotPath = "screenshots/" + screenshotName;

		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(screenshot, new File(screenshotPath));

			System.out.println("Screenshot captured: " + screenshotPath);

		} catch (IOException e) {
			System.err.println("Failed to capture screenshot for test case: " + testCaseName);
			e.printStackTrace();
		}
	}
}
