package com.aepl.pages;

import java.time.Duration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OtaPage {
	
	// Global variables
	private final WebDriver driver;
	private final WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(OtaPage.class);

	
	// Constructor
	public OtaPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	// Methods goes here
	public void clickNavBarAndDropdown() {}
	
	public void checkButtons() {} // all three buttons on page
	
	public void checkSearchBoxAndTable() {}
	
	public void checkPagination() {}
	
	public void checkActionButtons() {}
	
	public void checkOtaBatchReportButton() {}
	
	public void checkOtaMasterButton() {}
	
	public void checkCreateNewBatchButton() {}
	
	// Helping methods goes here
	
}
