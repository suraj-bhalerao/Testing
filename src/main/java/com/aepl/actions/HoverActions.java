package com.aepl.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HoverActions {
	private WebDriver driver;
	private Actions actions;

	public HoverActions(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}

	public void hoverOverElement(WebElement element) {
		actions.moveToElement(element).perform();
	}
}
