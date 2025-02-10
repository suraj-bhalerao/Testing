package com.aepl.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aepl.util.CommonMethod;

public class DispachedDevicePage {

	// Global variables
	private final WebDriver driver;
	private final WebDriverWait wait;
	private CommonMethod commonMethod;
	private final Logger logger = LogManager.getLogger(DispachedDevicePage.class);

	private final By dropDown = By.xpath("//span[@class='headers_custom color_3D5772']");
	private final By DispatchedDevice = By.xpath("//a[@routerlink='dispatch-device-page']");
	private final By AddDispatchedDevice = By.xpath("//button[contains(.,'Add Dispatched Device')]");
	private final By ChooseFile = By.id("txtFileUpload");
	private CommonMethod uploadFileAndGetFileName;
	private final By downloadSampleButton = By.xpath("//button[contains(.,'Download Sample')]");
	private By fileInput = By.id("C:\\Users\\Dhananjay Jagtap\\Downloads\\Sample_Dispatch_Sheet.xlsx");
	private By uploadButton = By.id("txtFileUpload");
	private By uploadedFileName = By.id("Sample_Dispatch_Sheet (3).xlsx");

	public DispachedDevicePage(WebDriver driver) {
		this.driver = driver;

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.commonMethod = new CommonMethod(driver);
		this.uploadFileAndGetFileName = new CommonMethod(driver);
	}

	// Methods Goes here
	public void clickNavBar() {
		List<WebElement> navBarLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropDown));

		boolean isClicked = false;
		for (WebElement link : navBarLinks) {
			if (link.getText().equalsIgnoreCase("Device Utility")) {
				link.click();
				isClicked = true;
				break;
			}
		}
		if (!isClicked) {
			throw new RuntimeException("Failed to find and click on 'Device Utility' in the navigation bar.");
		}
	}

	public String clickDispatchedDevice() {
		// Click on the element 'Device Model' and return the current URL
		try {
			WebElement DispatchedDeviceLink = wait
					.until(ExpectedConditions.visibilityOfElementLocated(DispatchedDevice));
			DispatchedDeviceLink.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Dispatched Device option.", e);
			throw new RuntimeException("Failed to click on Dispatched Device option", e);
		}
	}

	public String clickAddDispatchedDevice() {
		// Click on the element 'Add Device Model' and return the current URL
		try {
			WebElement AddDispatchedDeviceBtn = wait
					.until(ExpectedConditions.visibilityOfElementLocated(AddDispatchedDevice));
			AddDispatchedDeviceBtn.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while clicking on Add Dispatched Device button.", e);
			throw new RuntimeException("Failed to click on Add Dispatched Device button", e);
		}
	}

	public void clickdownloadSample() {
		// Click on the element 'Download Sample' and verify the download
		try {
			WebElement downloadButton = wait.until(ExpectedConditions.visibilityOfElementLocated(downloadSampleButton));

			commonMethod.checkReportDownloadForAllbuttons(downloadButton);
			downloadButton.click();
			wait.until(ExpectedConditions.invisibilityOf(downloadButton));
	    // Click on the element 'Download Sample' and verify the download
	    try {
	        WebElement downloadButton = wait.until(ExpectedConditions.visibilityOfElementLocated(downloadSampleButton));
	        
	        commonMethod.checkReportDownloadForAllbuttons(downloadButton);
	        downloadButton.click();
	        wait.until(ExpectedConditions.invisibilityOf(downloadButton));
	    } catch (Exception e) {
	        logger.error("Error while downloading the sample report.", e);
	        throw new RuntimeException("Failed to download the sample report", e);
	    }
	}

	public String clickChooseFileBtn() {
		// Click on the element 'Add Device Model' and return the current URL
		try {
			WebElement ChooseFileBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(ChooseFile));
			ChooseFileBtn.click();
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Error while downloading the sample report.", e);
			throw new RuntimeException("Failed to download the sample report", e);
		}
	}


	public void uploadFile(String fileInput) throws AWTException {
		((WebElement) ChooseFile).click(); // Click "Choose File" button to open the file dialog
		handleFileUpload(fileInput);
		((WebElement) uploadButton).click(); // Click "Upload" button after selecting the file
	}
//	public String uploadFile(String filePath) {
//		return commonMethod.uploadFileAndGetFileName(filePath);
//	}


	private void handleFileUpload(String fileInput) throws AWTException {
		// Copy file path to clipboard
		StringSelection selection = new StringSelection(fileInput);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

		// Use Robot to paste file path and press Enter
		Robot robot = new Robot();
		robot.delay(2000);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

//	public String uploadFile(String filePath) {
//	    try {
//	        logger.info("Starting file upload for: " + filePath);
//
//	        WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(fileInput));
//	        WebElement uploadBtn = wait.until(ExpectedConditions.elementToBeClickable(uploadButton));
//
//	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", inputField);
//	        inputField.sendKeys(filePath);
//	        logger.info("File path entered successfully.");
//
//	        if (!uploadBtn.isEnabled()) {
//	            logger.warn("Upload button is disabled. Cannot proceed with file upload.");
//	            return null;
//	        }
//
//	        uploadBtn.click();
//	        logger.info("Clicked on the upload button.");
//
//	        WebElement uploadedFileElement = wait.until(ExpectedConditions.visibilityOfElementLocated(uploadedFileName));
//	        String uploadedFile = uploadedFileElement.getText();
//	        logger.info("File uploaded successfully. Uploaded file name: " + uploadedFile);
//
//	        return uploadedFile;
//	    } catch (Exception e) {
//	        logger.error("Failed to upload file.", e);
//	        return null;
//	    }
//	}

//	public String clickChooseFileBtn() {
//		// Click on the element 'Add Device Model' and return the current URL
//		try {
//			WebElement ChooseFileBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(ChooseFile));
//			ChooseFileBtn.click();
//			return driver.getCurrentUrl();
//		} catch (Exception e) {
//			logger.error("Error while clicking on Choose File button.", e);
//			throw new RuntimeException("Failed to click on Choose File button", e);
//		}
//	}

}
