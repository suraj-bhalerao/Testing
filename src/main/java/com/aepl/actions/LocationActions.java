package com.aepl.actions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(LocationActions.class);
	
	public LocationActions(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	public void selectDynamicLocation(By inputLocator, By suggestionsLocator, String locationToSelect) {
		WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));
		inputField.sendKeys(locationToSelect.substring(0, 3));

		List<WebElement> suggestions = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestionsLocator));

		for (WebElement suggestion : suggestions) {
			if (suggestion.getText().equalsIgnoreCase(locationToSelect)) {
				suggestion.click();
				return;
			}
		}
		throw new RuntimeException("Location not found in suggestions: " + locationToSelect);
	}
}
