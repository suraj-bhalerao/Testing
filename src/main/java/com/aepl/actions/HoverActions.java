package com.aepl.actions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author Suraj_Bhalerao
 * @version 1.0
 * 
 * 
 * The HoverActions class provides utility methods for performing hover actions
 * on web elements using Selenium WebDriver.
 * 
 * <p>
 * Features:
 * <ul>
 *   <li>Allows hovering over specific web elements.</li>
 *   <li>Leverages Selenium's Actions class for complex interactions.</li>
 *   <li>Includes robust logging using Log4j for debugging and tracking actions.</li>
 * </ul>
 * 
 * <p>
 * Dependencies:
 * <ul>
 *   <li>Selenium WebDriver</li>
 *   <li>Apache Log4j</li>
 * </ul>
 * 
 * <p>
 * Logging:
 * <ul>
 *   <li>Logs detailed information about actions performed.</li>
 *   <li>Captures errors and exceptions for troubleshooting.</li>
 * </ul>
 * 
 */
public class HoverActions {
    private WebDriver driver;
    private Actions actions;

    private static final Logger logger = LogManager.getLogger(HoverActions.class);

    /**
     * Constructor to initialize the HoverActions instance.
     * 
     * @param driver the WebDriver instance used to control the browser.
     * @throws IllegalArgumentException if the driver is null.
     */
    public HoverActions(WebDriver driver) {
        if (driver == null) {
            logger.error("WebDriver instance is null. Cannot initialize HoverActions.");
            throw new IllegalArgumentException("WebDriver instance cannot be null");
        }

        this.driver = driver;
        this.actions = new Actions(this.driver);
        logger.info("HoverActions instance initialized successfully.");
    }

    /**
     * Hovers over the specified WebElement.
     * 
     * @param element the WebElement to hover over.
     * @throws IllegalArgumentException if the element is null.
     */
    public void hoverOverElement(WebElement element) {
        if (element == null) {
            logger.error("WebElement is null. Cannot perform hover action.");
            throw new IllegalArgumentException("WebElement cannot be null");
        }

        logger.info("Attempting to hover over element: " + element.toString());
        try {
            actions.moveToElement(element).build().perform();
            logger.info("Hover action performed successfully on element: " + element.toString());
        } catch (Exception e) {
            logger.error("Error occurred while hovering over element: " + element.toString(), e);
            throw e; // Re-throw exception for upstream handling
        }
    }
}
