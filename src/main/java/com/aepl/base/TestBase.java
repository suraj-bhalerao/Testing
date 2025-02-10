package com.aepl.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aepl.util.ConfigProperties;
import com.aepl.util.WebDriverFactory;

public class TestBase {

	protected WebDriver driver;
	protected final Logger logger = LogManager.getLogger(TestBase.class);

	@BeforeSuite
	public void printBanner() {
		try {
			String banner = new String(Files.readAllBytes(Paths.get("src/main/resources/banner.txt")));
			System.out.println(banner);
		} catch (IOException e) {
			System.out.println("Custom Banner not found!");
		}
	}

	@BeforeMethod
	public void zoomChrome() {
		if (driver != null) {
			((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
		}
	}

	@BeforeClass
	public void setUp() {
		ConfigProperties.initialize("qa");
		String browserType = ConfigProperties.getProperty("browser.type");
		logger.info("Setting up WebDriver for " + browserType + " browser.");
		driver = WebDriverFactory.getWebDriver(browserType);
		driver.manage().window().maximize();
		driver.get(ConfigProperties.getProperty("base.url"));
//		((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
		logger.info("Navigated to: " + ConfigProperties.getProperty("base.url"));
	}

//	@AfterClass
	public void tearDown() {
		if (driver != null) {
			logger.info("Closing the browser after all test classes and suite.");
			driver.quit();
		} else {
			logger.warn("Driver was null; no browser to close.");
		}
	}
}
