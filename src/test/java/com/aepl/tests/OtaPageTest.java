package com.aepl.tests;

import org.testng.annotations.BeforeClass;

import com.aepl.base.TestBase;
import com.aepl.pages.LoginPage;
import com.aepl.pages.OtaPage;
import com.aepl.util.ExcelUtility;

public class OtaPageTest extends TestBase {
	private LoginPage loginPage;
	private ExcelUtility excelUtility;
	private OtaPage otaPage;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(driver);
		otaPage = new OtaPage(driver);
		excelUtility = new ExcelUtility();
		excelUtility.initializeExcel("Change_Mobile_Test");
	}

	// Test Cases goes here...
}
