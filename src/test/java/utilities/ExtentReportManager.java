package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		/* SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
		   Date dt = new Date();
		   String currentdatetimestamp = df.format(dt);
		  
		 */
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());		// timestamp
		repName = "Test-Report-" + timeStamp +".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);						// location of the report
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report");					// Title of the report
		sparkReporter.config().setReportName("opencart Functional Testing");					// Name of the report					
		sparkReporter.config().setTheme(Theme.DARK);											// Theme of the report
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
			
		}
		
		
	}
	public void onTestSuccess(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName()+"  got successfully executed");
	}
	
	public void onTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+"  got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		String imgPath = new BaseClass().captureScreen(result.getName());
		test.addScreenCaptureFromPath(imgPath);
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
		
		
		// Automatically report will open with the below code
		
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// send the report automatically to the team members
	/*	
		try
		{
			URL = url = new URL("file:///"+System.getProperty("user.dir") + "\\reports\\" + repName);
			
			// Create email message
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.getSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("xyza@def.com","Demo@12345"));
			email.setSSLOnConnect(true);
			email.setFrom("xyza@def.com");
			email.setSubject("Extent Report Test Result");
			email.setMsg("Please find attached report");
			email.addTo("panneer.testing@gmail.com");
			email.attach(url,"extent report","Please check report");
			email.send();
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		*/
	}

}
