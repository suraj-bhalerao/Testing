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

	/**
	 * Initializes the WebDriver instance and sets up the test environment.
	 * <p>
	 * This method performs the following:
	 * <ul>
	 * <li>Loads configuration properties for the specified environment (e.g.,
	 * QA).</li>
	 * <li>Creates a WebDriver instance based on the configured browser type.</li>
	 * <li>Maximizes the browser window and navigates to the base URL.</li>
	 * </ul>
	 * <p>
	 * Logs detailed information about the setup process for debugging purposes.
	 */
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

	/**
	 * Cleans up the WebDriver instance and closes the browser session.
	 * <p>
	 * Ensures that the browser is properly closed after all test methods in the
	 * class are executed. If the WebDriver instance is null, logs a warning.
	 */

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
