package com.orangehrm.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.orangehrm.base.TestBase;

public class LoginTest extends TestBase{
	
	@Test
	public void loginAsAdmin() {
		
		log.debug("Inside Login Test");
		type("loginUsername_Id", "admin");
		type("loginPassword_Id", "admin123");
		click("loginBtn_Id");
		Assert.assertTrue(isElementPresent(By.id(OR.getProperty("dashboardWelcome_Id"))), "Login not successfull");
		log.debug("Login Test executed successfully");
		//Assert.fail("Login not successful");
		
	}
}
