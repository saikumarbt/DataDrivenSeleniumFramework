package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.orangehrm.base.TestBase;

public class TestUtil extends TestBase {

	public static String screenshotPath;
	public static String screenshotName;

	public static void captureScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
	}

	@DataProvider(name = "dp")
	public Object[][] getData(Method m) {

		String sheetName = m.getName();
		System.out.println(sheetName);

		int rows = excel.getRowCount(sheetName);
		System.out.println(" Row Count: "+ rows);
		int cols = excel.getColumnCount(sheetName);
		System.out.println(" Col Count: "+ cols);
		Object[][] data = new Object[rows - 1][1];

		Hashtable<String, String> table = null;
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			table= new Hashtable<String, String>();
			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
				System.out.println(table.get(excel.getCellData(sheetName, colNum, 1)));
			}
		}
		return data;
	}

	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		String sheetName = "testsuite";
		int rows = excel.getRowCount(sheetName);
		for(int rNum=2; rNum<=rows; rNum++) {
			String testcase = excel.getCellData(sheetName, "TCID", rNum);
			if(testcase.equalsIgnoreCase(testName)) {
				String runmode = excel.getCellData(sheetName, "Runmode", rNum);
				if(runmode.equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}
		}
		return false;
		
	}
	
	public static String getCurrentDateAndTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss");  
	    String currentdate =  formatter.format(date).toString(); 
	    
	    return currentdate; 
	 
	}
}
