package com.aepl.actions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HoverActions {
    private final WebDriver driver;
    private final Actions actions;

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

        logger.info("Attempting to hover over element: " + element);
        try {
            actions.moveToElement(element).build().perform();
            logger.info("Hover action performed successfully on element: " + element);
        } catch (Exception e) {
            logger.error("Error occurred while hovering over element: " + element, e);
            throw e; // Re-throw exception for upstream handling
        }
    }
}
