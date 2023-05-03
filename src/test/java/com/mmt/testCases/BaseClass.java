package com.mmt.testCases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.ScreenCapture;
import com.github.dockerjava.transport.DockerHttpClient.Request.Method;
import com.mmt.utilities.ReadConfig;
import com.mmt.utilities.Reporting;

import dev.failsafe.internal.util.Durations;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {

	ReadConfig readconfig=new ReadConfig();
	
	public String baseURL=readconfig.getApplicationURL();
	public String br=readconfig.getBrowser();
	public String username=readconfig.getUsername();
	public String password=readconfig.getPassword();
	public static WebDriver driver;
	
	
	public static Logger logger;
	
	//@Parameters("browser")
	@BeforeClass
	public void setup()
	{		
		logger = Logger.getLogger("MakeMyTrip");
		PropertyConfigurator.configure("Log4j.properties");
		
		if(br.equals("chrome"))
		{	
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		else
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}		
				
		driver.get(baseURL);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
	
	public static void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}
	
	public static void captureScreen2(String ValidationMsg) throws IOException {
		
		
		
	}
	
	public static void Validate(Boolean result, String VaidationMsg) throws IOException
	{
		if(result) 
		{
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			
			String SSName = randomestring();
			File target = new File(System.getProperty("user.dir") + "/Screenshots/" + SSName + ".png");
			String screenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+SSName+".png"; 
			//ts.SaveAsFile(screenshotPath);
			FileUtils.copyFile(source, target);
			System.out.println("Screenshot taken");
			//loggerr=extent.createTest(tr.getName());
			//loggerr.log(Status.PASS, "Screenshot is below:"+ "<br />", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath));
			Reporting.loggerr.log(Status.PASS,VaidationMsg + "<br/>", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		}
		else
		{
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String SSName = randomestring();
			File target = new File(System.getProperty("user.dir") + "/Screenshots/" + SSName + ".png");
			String screenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+SSName+".png"; 
			FileUtils.copyFile(source, target);
			System.out.println("Screenshot taken");
			Reporting.loggerr.log(Status.FAIL,VaidationMsg + "<br/>", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		}
	}
	
	public static String randomestring()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(6);
		return(generatedstring);
	}
	
	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}
	
	public static void Pause (long time)
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void WaitForClickable (int Sec, WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(Sec));
		 wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	
	public static Boolean IsElementPresent(String Element)
	{		
			return driver.findElement(By.xpath(Element)).isDisplayed();					
		
	}
	
	
	
	 
		
}
