package com.aepl.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseActions {
	private WebDriver driver;
	private Actions actions;

	public MouseActions(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}

	// This is for scrolling 
	public void moveToElement(WebElement element) {
		actions.moveToElement(element).build().perform();
	}

	public void clickElement(WebElement element) {
		actions.click(element).build().perform();
	}

	public void doubleClickElement(WebElement element) {
		actions.doubleClick(element).build().perform();
	}

	public void rightClickElement(WebElement element) {
		actions.contextClick(element).build().perform();
	}
}
