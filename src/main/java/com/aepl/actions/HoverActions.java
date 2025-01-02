package com.aepl.actions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HoverActions {
	private WebDriver driver;
	private Actions actions;
	
	private static final Logger logger = LogManager.getLogger(HoverActions.class);

	public HoverActions(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}

	public void hoverOverElement(WebElement element) {
		actions.moveToElement(element).build().perform();
	}
}
