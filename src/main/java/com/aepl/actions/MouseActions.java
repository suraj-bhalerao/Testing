package com.aepl.actions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseActions {
	private final WebDriver driver;
	private final Actions actions;

	private static final Logger logger = LogManager.getLogger(MouseActions.class);

	public MouseActions(WebDriver driver) {
		if (driver == null) {
			logger.error("WebDriver instance is null. Cannot initialize MouseActions.");
			throw new IllegalArgumentException("WebDriver instance cannot be null");
		}

		this.driver = driver;
		this.actions = new Actions(this.driver);
		logger.info("MouseActions instance initialized successfully.");
	}

	public void moveToElement(WebElement element) {
		if (element == null) {
			logger.error("WebElement is null. Cannot perform moveToElement action.");
			throw new IllegalArgumentException("WebElement cannot be null");
		}

		logger.info("Attempting to move to element: " + element);
		try {
			actions.moveToElement(element).build().perform();
			logger.info("Mouse moved to element successfully.");
		} catch (Exception e) {
			logger.error("Error occurred while moving to element: " + element, e);
			throw e;
		}
	}

	public void clickElement(WebElement element) {
		if (element == null) {
			logger.error("WebElement is null. Cannot perform clickElement action.");
			throw new IllegalArgumentException("WebElement cannot be null");
		}

		logger.info("Attempting to click element: " + element);
		try {
			actions.click(element).build().perform();
			logger.info("Element clicked successfully.");
		} catch (Exception e) {
			logger.error("Error occurred while clicking element: " + element, e);
			throw e;
		}
	}

	public void doubleClickElement(WebElement element) {
		if (element == null) {
			logger.error("WebElement is null. Cannot perform doubleClickElement action.");
			throw new IllegalArgumentException("WebElement cannot be null");
		}

		logger.info("Attempting to double-click element: " + element);
		try {
			actions.doubleClick(element).build().perform();
			logger.info("Element double-clicked successfully.");
		} catch (Exception e) {
			logger.error("Error occurred while double-clicking element: " + element, e);
			throw e;
		}
	}

	public void rightClickElement(WebElement element) {
		if (element == null) {
			logger.error("WebElement is null. Cannot perform rightClickElement action.");
			throw new IllegalArgumentException("WebElement cannot be null");
		}

		logger.info("Attempting to right-click element: " + element);
		try {
			actions.contextClick(element).build().perform();
			logger.info("Element right-clicked successfully.");
		} catch (Exception e) {
			logger.error("Error occurred while right-clicking element: " + element, e);
			throw e;
		}
	}

	// new method added : select content on the screen and paste it
	public void selectAndCopy(WebElement startElement, WebElement endElement) {
		if (startElement == null || endElement == null) {
			logger.error("WebElement(s) are null. Cannot perform select and copy action.");
			throw new IllegalArgumentException("WebElement(s) cannot be null");
		}

		logger.info("Attempting to select text by dragging from " + startElement + " to " + endElement);
		try {
			actions.clickAndHold(startElement).moveToElement(endElement).release().build().perform();
			logger.info("Text selected successfully.");

			actions.sendKeys(Keys.CONTROL, "c").build().perform();
			logger.info("Copied selected text.");

		} catch (Exception e) {
			logger.error("Error occurred while selecting and copying text.", e);
			throw e;
		}
	}

	public void pasteText(WebElement targetElement) {
		if (targetElement == null) {
			logger.error("WebElement is null. Cannot perform paste action.");
			throw new IllegalArgumentException("WebElement cannot be null");
		}

		logger.info("Attempting to paste copied text at element: " + targetElement);
		try {
			actions.moveToElement(targetElement).click().build().perform();

			actions.sendKeys(Keys.CONTROL, "v").build().perform();
			logger.info("Pasted text successfully.");

		} catch (Exception e) {
			logger.error("Error occurred while pasting text.", e);
			throw e;
		}
	}
}
