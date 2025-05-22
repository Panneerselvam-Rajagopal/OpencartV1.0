package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


/*
  
 IT IS HAVING ONLY SETUP(), TEARDOWN() AND SOME COMMON METHODS. THESE METHODS ARE RE-USABLE FOR ALL THE TEST CASE METHODS. SO WE ARE USING
IN THE BASE CLASS AND INHERIT INTO THE TEST CASE CLASSES.

*/
public class BaseClass {
	
public static WebDriver driver;
public Logger logger;		// Log4j2
Properties prop;			// To use config.properties file
	
	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException
	{
		
		FileReader file = new FileReader("./src//test//resources//config.properties");
		prop = new Properties();
		prop.load(file);
		
		
		logger = LogManager.getLogger(this.getClass());			// Log4j2	
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\santh\\Downloads\\chromedriver-win64 (3)\\chromedriver-win64\\chromedriver.exe");		
		switch (br.toLowerCase())								// Cross browser and parallel testing using xml
		{
		case "chrome"  : driver = new ChromeDriver(); break;
		case "edge"    : driver = new EdgeDriver(); break;
		case "firefox" : driver = new FirefoxDriver(); break;
		default		   : System.out.println("Invalid browser name ..."); return;
		}
				
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(prop.getProperty("appURL"));									// reading url from config.properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	// Create a user defined method to generate random string, random number and random alpha numeric
	public String randomString()
	{
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
			
	}
		
	public String randomNumber()
	{
		String generatednumber = RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}
		
		
	public String randomAlphaNumeric()
	{
		String generatedstring = RandomStringUtils.randomAlphabetic(3);
		String generatednumber = RandomStringUtils.randomNumeric(3);
		return (generatedstring+"@"+generatednumber);
			
	}
	
	public String captureScreen(String tname)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		TakesScreenshot	TakesScreenshot = (TakesScreenshot) driver;
		File sourcefile = TakesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String TargetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetfile = new File(TargetFilePath);
		
		sourcefile.renameTo(targetfile);
		
		return TargetFilePath;
	}


}
