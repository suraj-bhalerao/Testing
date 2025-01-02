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
 * Class: DragAndDropActions
 * --------------------------
 * Provides utility methods to perform drag-and-drop actions using Selenium WebDriver.
 * 
 * Methods:
 * --------
 * - `dragAndDrop(WebElement source, WebElement target)`:
 *     Performs a drag-and-drop action from the source element to the target element.
 *     - Logs the start and end of the operation, including meaningful details about the elements.
 *     - Handles any exceptions and rethrows them for further handling.
 * 
 * - `dragAndDropByOffset(WebElement source, int xOffset, int yOffset)`:
 *     Drags the source element by a specified offset in the x and y directions.
 *     - Validates the input and logs the operation details.
 *     - Handles exceptions in a consistent manner.
 * 
 * - `getElementDescription(WebElement element)`:
 *     A private utility method to provide a meaningful description of a WebElement,
 *     including its tag name and text content.
 * 
 * Logging:
 * --------
 * Logs are generated using Apache Log4j to track the flow of operations and errors.
 * 
 * Exception Handling:
 * -------------------
 * Methods validate their inputs and handle errors appropriately to avoid silent failures.
 */


/**
 * Utility class to handle drag-and-drop actions using Selenium WebDriver.
 */
public class DragAndDropActions {
	private WebDriver driver;
	private Actions actions;

	private static final Logger logger = LogManager.getLogger(DragAndDropActions.class);

	/**
	 * Constructor for initializing DragAndDropActions with WebDriver.
	 * 
	 * @param driver the WebDriver instance.
	 */
	public DragAndDropActions(WebDriver driver) {
		if (driver == null) {
			throw new IllegalArgumentException("WebDriver instance cannot be null");
		}
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}

	/**
	 * Performs drag-and-drop from the source element to the target element.
	 *
	 * @param source the WebElement to drag.
	 * @param target the WebElement to drop onto.
	 */
	public void dragAndDrop(WebElement source, WebElement target) {
		if (source == null || target == null) {
			logger.error("Source or target element cannot be null");
			throw new IllegalArgumentException("Source or target WebElement cannot be null");
		}

		try {
			logger.info("Dragging element from '" + source.getText() + "' to '" + target.getText() + "'");
			actions.dragAndDrop(source, target).build().perform();
			logger.info("Drag-and-drop operation completed successfully.");
		} catch (Exception e) {
			logger.error("Error during drag-and-drop operation: " + e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Performs drag-and-drop from the source element to an offset.
	 *
	 * @param source  the WebElement to drag.
	 * @param xOffset the horizontal offset.
	 * @param yOffset the vertical offset.
	 */
	
	public void dragAndDropByOffset(WebElement source, int xOffset, int yOffset) {
		if (source == null) {
			logger.error("Source element cannot be null");
			throw new IllegalArgumentException("Source WebElement cannot be null");
		}

		try {
			logger.info("Dragging element '" + source.getText() + "' to offset (" + xOffset + ", " + yOffset + ")");
			actions.dragAndDropBy(source, xOffset, yOffset).build().perform();
			logger.info("Drag-and-drop operation by offset completed successfully.");
		} catch (Exception e) {
			logger.error("Error during drag-and-drop by offset: " + e.getMessage(), e);
			throw e;
		}
	}
}