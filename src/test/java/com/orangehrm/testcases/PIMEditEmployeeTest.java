package com.orangehrm.testcases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.base.TestBase;
import com.orangehrm.utilities.TestUtil;

public class PIMEditEmployeeTest extends TestBase {
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void editEmployee(Hashtable<String, String> data) throws InterruptedException {
		click("editEmployee_edit_save_Id");
		Thread.sleep(10);
		if(data.get("gender").equals("Male")) {
		click("editEmployee_male_Id");
		} else {
			click("editEmployee_female_Id");
		}
		
		select("editEmployee_maritalStatus_Id",data.get("maritalStatus"));
	
		select("editEmployee_nationality_Id",data.get("nationality"));
		type("editEmployee_dob_Id", data.get("dob"));	
		Thread.sleep(30);
		click("editEmployee_edit_save_Id");
		Thread.sleep(30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("profile-pic")));
		Assert.assertTrue(isElementPresent(By.id("profile-pic")), "Employee not added");
		
	}
}
