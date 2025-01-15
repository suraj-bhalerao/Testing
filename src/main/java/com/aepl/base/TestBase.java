package com.aepl.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

import com.aepl.util.ConfigProperties;
import com.aepl.util.WebDriverFactory;

public class TestBase {

	protected static WebDriver driver;
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

//    @AfterClass
	public void tearDown() {
		if (driver != null) {
			logger.info("Closing the browser after all test classes.");
			driver.quit();
		} else {
			logger.warn("Driver was null; no browser to close.");
		}
	}
}
