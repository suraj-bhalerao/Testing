package com.aepl.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DealearFotaPage {
	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(DealearFotaPage.class);

	public DealearFotaPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		logger.info("Initialized the driver and wait ");
	}

	// Locators goes here
	private By navBarLink = By.xpath("//*[@id=\"navbarDropdownProfile\"]/span");
	private By dealerFota = By.xpath("//a[@class=\"dropdown-item ng-star-inserted\"][6]");
	private By addApprovedFileBtn = By.xpath("/html/body/app-root/app-dealer-fota/div/div/div[2]/button");
	private By fileNameInput = By.xpath("//input[@placeholder='Enter File Name']");
	private By saveFileButton = By.xpath("//button[@class='btn btn-primary w-100']");
	private By tableRowsLocator = By.xpath("//tr[@class=\"odd text-center ng-star-inserted\"]");
	
	// Methods goes here
	public void clickNavBar() {
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navBarLink));

		boolean isClicked = false;
		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Device Utility")) {
				link.click();
				isClicked = true;
				clickDropDownOption();
				break;
			}
		}
		if (!isClicked) {
			throw new RuntimeException("Failed to find and click on 'Device Utility' in the navigation bar.");
		}
	}
	
	public String clickDropDownOption() {
		try {
			WebElement dealerFotaLink = wait.until(ExpectedConditions.visibilityOfElementLocated(dealerFota));
			dealerFotaLink.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Change Mobile option.", e);
			throw new RuntimeException("Failed to click on Change Mobile option", e);
		}
	}
	
	public void clickAddApprovedFileButton() {
		logger.info("Clicking on the 'Add Approved File' Button");
		WebElement fileBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(addApprovedFileBtn));
		
		if(fileBtn.isEnabled() && fileBtn.isDisplayed()) {
			logger.info("Add Approved File buttton is visible and clickable");
			fileBtn.click();
			logger.info("Add Approved File button");
		}
	}
	
	public void addNewFileAndValidate() {
	    // File Adding 
	    logger.info("Trying to add the new file");
	    WebElement inputBox = wait.until(ExpectedConditions.visibilityOfElementLocated(fileNameInput));
	    WebElement fileBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(saveFileButton));
	    
	    String fileName = "Testing File Name";
	    logger.info("Entering file name in the input box");
	    inputBox.sendKeys(Keys.ENTER);
	    inputBox.sendKeys(fileName);
	    
	    // Capture the current date and time in the specified format
	    String dateTimeFormat = "MM/dd/yyyy, hh:mm:ss a";
	    SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
	    String capturedDateTime = formatter.format(new Date());
	    
	    logger.info("Clicking on the save file button");
	    fileBtn.click();
	    
	    // Pause briefly to allow the table to update
	    try {
	        Thread.sleep(2000); // Adjust as per your application's update time
	    } catch (InterruptedException e) {
	        logger.error("Thread interrupted while waiting for table update", e);
	    }
	    
	    // Validate the uploaded file in the table
	    logger.info("Validating the uploaded file in the table");
	    List<WebElement> tableRows = driver.findElements(tableRowsLocator);
	    boolean isFileValidated = false;
	    
	    for (WebElement row : tableRows) {
	        List<WebElement> cells = row.findElements(By.tagName("td"));
	        
	        if (cells.size() > 0) {
	            String rowFileName = cells.get(0).getText(); 
	            String rowDateTime = cells.get(1).getText(); 
	            
	            if (fileName.equals(rowFileName) && capturedDateTime.equals(rowDateTime)) {
	                logger.info("File name and date-time validated successfully in the table");
	                isFileValidated = true;
	                break;
	            }
	        }
	    }
	    
	    if (!isFileValidated) {
	        logger.error("File validation failed. File name or date-time not found in the table");
	        throw new RuntimeException("File validation failed");
	    }
	}
}
