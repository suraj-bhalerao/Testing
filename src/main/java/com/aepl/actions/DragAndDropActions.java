package com.aepl.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropActions {
	private WebDriver driver;
	private Actions actions;

	public DragAndDropActions(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(this.driver);
	}

	public void dragAndDrop(WebElement source, WebElement target) {
		actions.dragAndDrop(source, target).build().perform();
	}

	public void dragAndDropByOffset(WebElement source, int xOffset, int yOffset) {
		actions.dragAndDropBy(source, xOffset, yOffset).build().perform();
	}
}
