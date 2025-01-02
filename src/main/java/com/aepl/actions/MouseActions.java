package com.aepl.actions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author YourName
 * @version 1.0
 * 
 * 
 * The MouseActions class provides utility methods for performing various mouse interactions 
 * on web elements using Selenium WebDriver.
 * 
 * <p>
 * Features:
 * <ul>
 *   <li>Allows moving to an element (useful for scrolling or hover effects).</li>
 *   <li>Supports single click, double click, and right-click actions.</li>
 *   <li>Leverages Selenium's Actions class for robust interaction simulation.</li>
 *   <li>Includes detailed logging for debugging and tracking actions.</li>
 * </ul>
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
 *   <li>Logs detailed information about mouse interactions performed.</li>
 *   <li>Captures errors and exceptions for troubleshooting.</li>
 * </ul>
 */
public class MouseActions {
    private WebDriver driver;
    private Actions actions;

    private static final Logger logger = LogManager.getLogger(MouseActions.class);

    /**
     * Constructor to initialize the MouseActions instance.
     * 
     * @param driver the WebDriver instance used to control the browser.
     * @throws IllegalArgumentException if the WebDriver instance is null.
     */
    public MouseActions(WebDriver driver) {
        if (driver == null) {
            logger.error("WebDriver instance is null. Cannot initialize MouseActions.");
            throw new IllegalArgumentException("WebDriver instance cannot be null");
        }

        this.driver = driver;
        this.actions = new Actions(this.driver);
        logger.info("MouseActions instance initialized successfully.");
    }

    /**
     * Moves the mouse to the specified WebElement.
     * 
     * @param element the WebElement to move the mouse to.
     */
    public void moveToElement(WebElement element) {
        if (element == null) {
            logger.error("WebElement is null. Cannot perform moveToElement action.");
            throw new IllegalArgumentException("WebElement cannot be null");
        }

        logger.info("Attempting to move to element: " + element.toString());
        try {
            actions.moveToElement(element).build().perform();
            logger.info("Mouse moved to element successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while moving to element: " + element.toString(), e);
            throw e;
        }
    }

    /**
     * Clicks the specified WebElement.
     * 
     * @param element the WebElement to click.
     */
    public void clickElement(WebElement element) {
        if (element == null) {
            logger.error("WebElement is null. Cannot perform clickElement action.");
            throw new IllegalArgumentException("WebElement cannot be null");
        }

        logger.info("Attempting to click element: " + element.toString());
        try {
            actions.click(element).build().perform();
            logger.info("Element clicked successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while clicking element: " + element.toString(), e);
            throw e;
        }
    }

    /**
     * Double-clicks the specified WebElement.
     * 
     * @param element the WebElement to double-click.
     */
    public void doubleClickElement(WebElement element) {
        if (element == null) {
            logger.error("WebElement is null. Cannot perform doubleClickElement action.");
            throw new IllegalArgumentException("WebElement cannot be null");
        }

        logger.info("Attempting to double-click element: " + element.toString());
        try {
            actions.doubleClick(element).build().perform();
            logger.info("Element double-clicked successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while double-clicking element: " + element.toString(), e);
            throw e;
        }
    }

    /**
     * Right-clicks the specified WebElement.
     * 
     * @param element the WebElement to right-click.
     */
    public void rightClickElement(WebElement element) {
        if (element == null) {
            logger.error("WebElement is null. Cannot perform rightClickElement action.");
            throw new IllegalArgumentException("WebElement cannot be null");
        }

        logger.info("Attempting to right-click element: " + element.toString());
        try {
            actions.contextClick(element).build().perform();
            logger.info("Element right-clicked successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while right-clicking element: " + element.toString(), e);
            throw e;
        }
    }
}
