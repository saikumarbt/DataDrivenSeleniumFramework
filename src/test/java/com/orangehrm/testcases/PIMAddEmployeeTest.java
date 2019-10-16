package com.orangehrm.testcases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangehrm.base.TestBase;
import com.orangehrm.utilities.TestUtil;

public class PIMAddEmployeeTest extends TestBase {

	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addEmployee(Hashtable<String, String> data) throws InterruptedException {
		
		/*if(!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode is set to NO");
		}*/
		click("menu_pim_Id");
		Thread.sleep(10);
		click("menu_pim_addEmployee_Id");
		type("addEmployee_firstName_Id", data.get("firstName"));
		type("addEmployee_lastName_Id", data.get("lastName"));
		Thread.sleep(10);
		click("addEmployee_SaveBtn_Id");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("profile-pic")));
		Assert.assertTrue(isElementPresent(By.id("profile-pic")), "Employee not added");
		
	}
	
	
	
}
