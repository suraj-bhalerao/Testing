package com.aepl.pages;

import java.time.Duration;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aepl.util.CommonMethod;

public class TicketDashboardPage {
	// Global Variable and instances
	private WebDriver driver;
	private WebDriverWait wait;
	private CommonMethod commmethod;
	private static final Logger logger = LogManager.getLogger(TicketDashboardPage.class);

	// Constructor
	public TicketDashboardPage(WebDriver driver) {
		super();
		this.driver = driver;
		this.commmethod = new CommonMethod(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));	
	}

	// Locators goes here
	private By navBarLink = By.xpath("//*[@id=\"navbarDropdownProfile\"]/span");
	private By ticketDash = By.xpath("//a[@class=\"dropdown-item ng-star-inserted\"][3]");

	
	
	// Methods goes here
	public void clickNavBar() {
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navBarLink));

		boolean isClicked = false;
		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Ticket Dashboard")) {
				link.click();
				isClicked = true;
				clickDropDownOption();
				break;
			}
		}
		if (!isClicked) {
			throw new RuntimeException("Failed to find and click on 'Ticket Dashboard' in the navigation bar.");
		}
	}

	public String clickDropDownOption() {
		try {
			WebElement ticketDashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(ticketDash));
			ticketDashboard.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Ticket Dashboard option.", e);
			throw new RuntimeException("Failed to click on Ticket Dashboard option", e);
		}
	}
}
