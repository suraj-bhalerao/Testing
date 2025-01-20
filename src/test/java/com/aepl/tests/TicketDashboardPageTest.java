package com.aepl.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aepl.base.TestBase;
import com.aepl.pages.LoginPage;
import com.aepl.pages.TicketDashboardPage;
import com.aepl.util.CommonMethod;
import com.aepl.util.ConfigProperties;
import com.aepl.util.ExcelUtility;

public class TicketDashboardPageTest extends TestBase {
    private LoginPage loginPage;
    private ExcelUtility excelUtility;
    private TicketDashboardPage ticketDashboard;

    @Override
    @BeforeClass
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver);
        ticketDashboard = new TicketDashboardPage(driver);
        excelUtility = new ExcelUtility();
        excelUtility.initializeExcel("Ticket_Dash_Test");
    }

    @Test(priority = 1)
    public void login() {
        loginPage.enterUsername(ConfigProperties.getProperty("valid.username"))
                .enterPassword(ConfigProperties.getProperty("valid.password")).clickLogin();
    }

    @Test(priority = 2)
    public void testClickNavBar() {
        logger.info("Testing the navigation for the Ticket Dashboard");
        String testCaseName = "Test Ticket Dashboard";
        String expectedResult = ConfigProperties.getProperty("ticket.dash");
        String actualResult = "";
        String result;
        try {
            logger.info("Trying to click on the nav bar links");
            ticketDashboard.clickNavBar();
            actualResult = driver.getCurrentUrl();
            result = expectedResult.equalsIgnoreCase(actualResult) ? "PASS" : "FAIL";
            logger.info("Result is : " + result);
            excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
        } catch (Exception e) {
            logger.warn("Error");
            CommonMethod.captureScreenshot(testCaseName);
            actualResult = driver.getCurrentUrl();
            result = expectedResult.equalsIgnoreCase(actualResult) ? "PASS" : "FAIL";
            excelUtility.writeTestDataToExcel(testCaseName, expectedResult, actualResult, result);
        }
    }
}
