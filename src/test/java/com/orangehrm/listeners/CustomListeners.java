package com.orangehrm.listeners;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.orangehrm.base.TestBase;
import com.orangehrm.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener {

	public void onTestStart(ITestResult result) {
		
		
		
		test = report.startTest(result.getName().toUpperCase());
		//runmodes - Y
		if(!TestUtil.isTestRunnable(result.getName(), excel)) {
			throw new SkipException("Skipping the test"+ result.getName()+ " as the run mode is No");
		}
		
	}

	public void onTestSuccess(ITestResult result) {
		test.log(LogStatus.PASS, result.getName().toUpperCase()+"PASS");
		report.endTest(test);
		report.flush();
		
	}

	public void onTestFailure(ITestResult result) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+"FAILED with exception : "+result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		Reporter.log("Click to see screenshot");
		Reporter.log("<a target = \"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br/>");
		Reporter.log("<br/>");
		Reporter.log("<a target = \"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
		report.endTest(test);
		report.flush();
	}

	public void onTestSkipped(ITestResult result) {

		test.log(LogStatus.SKIP, result.getName().toUpperCase()+ ": Skipped the test as the Runmode is NO");
		report.endTest(test);
		report.flush();
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
