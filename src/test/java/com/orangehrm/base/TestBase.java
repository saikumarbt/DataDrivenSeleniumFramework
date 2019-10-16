package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.utilities.ExcelReader;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This would be the base class and all initializations would be taken care like
 * : WebDriver Properties Logs ExtentReports DB Excel Mailing ReportNG
 * 
 * @author Sai Kumar
 *
 */

public class TestBase {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log=Logger.getLogger(TestBase.class.getName());
	public static ExcelReader excel = new ExcelReader("C:\\Workspace\\AutomationFramework\\OrangeHRMDataDriven\\src\\test\\resources\\excel\\TestData.xlsx");
	public static WebDriverWait wait;
	public ExtentReports report = ExtentManager.getInstance();
	public static ExtentTest test;
	public static Select iSelect;
	public static String browser;
	
	/**
	 * Setup method to initialize and to be called before any class / test cases
	 * @throws IOException 
	 */
	@BeforeSuite
	public void setUp() {
		 
		System.setProperty("current.date",TestUtil.getCurrentDateAndTime());
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		
		if(driver==null) {
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.info("Config file loaded..!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.info("OR file loaded..!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()) {
				browser = System.getenv("browser");
			} else {
				browser = config.getProperty("browser");
			}
			
			config.setProperty("browser", browser);
			
			if (config.getProperty("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				log.info("Chrome Browser launched");
			} else if (config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				log.info("Firefox Browser launched");
			} else if (config.getProperty("browser").equals("ie")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				log.info("IE Browser launched");
			}
			
			driver.get(config.getProperty("testsiteurl"));
			log.info("Navigated to :"+config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
			
		}
		
	}
	
	public void click(String locator) {
		if (locator.endsWith("_Id")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on: " + locator);
	}
	

	public void select(String locator, String value) {
		if (locator.endsWith("_Id")) {
		iSelect = new Select(driver.findElement(By.id(OR.getProperty(locator))));
		} else if (locator.endsWith("_XPATH")) {
			iSelect = new Select(driver.findElement(By.xpath(OR.getProperty(locator))));
		} else if (locator.endsWith("_CSS")) {
			iSelect = new Select(driver.findElement(By.cssSelector(OR.getProperty(locator))));
		}
		iSelect.selectByVisibleText(value);
		test.log(LogStatus.INFO, "Selecting in: " + locator + " value as " + value);
	}
	
	public void type(String locator, String value) {
		if (locator.endsWith("_Id")) {
			driver.findElement(By.id(OR.getProperty(locator))).clear();
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).clear();
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).clear();
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}
		test.log(LogStatus.INFO, "Typing in: " + locator + " entered value as " + value);
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch(NoSuchElementException e) {
			
			return false;
			
		}
	}
	
	public static void verifyEquals(String expected, String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			TestUtil.captureScreenshot();
			//REPORTNG
			Reporter.log("<br>"+"Verification Failure:" + t.getMessage() + "<br>");
			Reporter.log("<a target = \"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			//Extent Reports
			test.log(LogStatus.FAIL, "Verification failed with exception : "+ t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		}
	}
	

	/**
	 * Clean up activities like clearing or closing the instances
	 */
	@AfterSuite
	public void tearDown() {
		if(driver!=null) { 
		driver.manage().deleteAllCookies();
		driver.quit();
		log.debug("Test Execution completed");
		}
	}
}
