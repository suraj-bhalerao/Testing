package com.aepl.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LocationActions {
	private WebDriver driver;
	private WebDriverWait wait;

	public LocationActions(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	/**
	 * Select a location from a dynamic dropdown.
	 *
	 * @param inputLocator       The locator for the input field.
	 * @param suggestionsLocator The locator for the suggestions list.
	 * @param locationToSelect   The location to select from the suggestions.
	 */
	public void selectDynamicLocation(By inputLocator, By suggestionsLocator, String locationToSelect) {
		// Type into the input field
		WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));
		inputField.sendKeys(locationToSelect.substring(0, 3)); // Type first few characters

		// Wait for suggestions to appear
		List<WebElement> suggestions = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestionsLocator));

		// Select the matching suggestion
		for (WebElement suggestion : suggestions) {
			if (suggestion.getText().equalsIgnoreCase(locationToSelect)) {
				suggestion.click();
				return;
			}
		}
		throw new RuntimeException("Location not found in suggestions: " + locationToSelect);
	}
}
