package com.aepl.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aepl.util.ConfigProperties;
import com.aepl.util.WebDriverFactory;

public class TestBase {

	protected WebDriver driver;
	protected static final Logger logger = LogManager.getLogger(TestBase.class);

	@BeforeClass
	public void setUp() {
		ConfigProperties.initialize("qa");
		String browserType = ConfigProperties.getProperty("browser.type");
		logger.info("Setting up WebDriver for " + browserType + " browser.");
		driver = WebDriverFactory.getWebDriver(browserType);
		driver.manage().window().maximize();
		driver.get(ConfigProperties.getProperty("base.url"));
		logger.info("Navigated to: " + ConfigProperties.getProperty("base.url"));
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			logger.info("Closing the browser.");
			driver.quit();
		}
	}

	public void captureScreenshot(String testCaseName) {
		
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
