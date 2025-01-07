package com.aepl.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aepl.util.ConfigProperties;
import com.aepl.util.WebDriverFactory;

/**
 * @author Suraj_Bhalerao
 * @version 1.0
 * 
 * 
 * TestBase class serves as a foundational setup and teardown framework for 
 * test cases in a Selenium-based test automation suite.
 *
 * <p>
 * Key Responsibilities:
 * <ul>
 *   <li>Initializes and configures the WebDriver instance before executing tests.</li>
 *   <li>Manages browser sessions, including maximization and navigation to the base URL.</li>
 *   <li>Handles browser cleanup after tests are executed.</li>
 *   <li>Provides a utility method for capturing screenshots during test execution.</li>
 * </ul>
 * 
 * <p>
 * Key Features:
 * <ul>
 *   <li>Environment-specific property management using the ConfigProperties utility.</li>
 *   <li>Supports multiple browser types configured via properties.</li>
 *   <li>Logs detailed information about setup, teardown, and errors using Log4j.</li>
 * </ul>
 *
 * <p>
 * Usage Example:
 * <pre>
 * {@code
 * public class SampleTest extends TestBase {
 * 
 *     @Test
 *     public void testExample() {
 *         // Perform actions
 *         captureScreenshot("testExample");
 *     }
 * }
 * }
 * </pre>
 */
public class TestBase {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(TestBase.class);

    /**
     * Initializes the WebDriver instance and sets up the test environment.
     * <p>
     * This method performs the following:
     * <ul>
     *   <li>Loads configuration properties for the specified environment (e.g., QA).</li>
     *   <li>Creates a WebDriver instance based on the configured browser type.</li>
     *   <li>Maximizes the browser window and navigates to the base URL.</li>
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
     * Ensures that the browser is properly closed after all test methods in the class are executed.
     * If the WebDriver instance is null, logs a warning.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing the browser after all test classes.");
            driver.quit();
        } else {
            logger.warn("Driver was null; no browser to close.");
        }
    }

    /**
     * Captures a screenshot of the current browser state.
     * <p>
     * The screenshot is saved to the `screenshots/` directory with a name 
     * formatted as `testCaseName_timestamp.png`.
     * 
     * <p>
     * This method performs the following:
     * <ul>
     *   <li>Generates a unique name for the screenshot using the test case name and timestamp.</li>
     *   <li>Captures the screenshot and saves it to the designated path.</li>
     *   <li>Logs an error if the WebDriver instance is null or if an IOException occurs.</li>
     * </ul>
     * 
     * @param testCaseName the name of the test case for which the screenshot is captured.
     */
//    public void captureScreenshot(String testCaseName) {
//        if (driver == null) {
//            logger.error("Driver is null, unable to capture screenshot.");
//            return;
//        }
//
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String screenshotName = testCaseName + "_" + timestamp + ".png";
//
//        String screenshotPath = "screenshots/" + screenshotName;
//
//        try {
//            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            FileUtils.copyFile(screenshot, new File(screenshotPath));
//            logger.info("Screenshot captured: " + screenshotPath);
//        } catch (IOException e) {
//            logger.error("Failed to capture screenshot for test case: " + testCaseName, e);
//        }
//    }
}
