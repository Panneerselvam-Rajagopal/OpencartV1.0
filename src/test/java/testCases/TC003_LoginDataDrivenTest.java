package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDataDrivenTest extends BaseClass {
	
	
	
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven")		// getting data provider from different class
	public void verify_loginDDT(String email, String pwd, String expresult)
	{
		
		logger.info("***** Starting TC_003_Login Data Driven Test *****");
		
	try
	{

	// Home Page
	HomePage hp = new HomePage(driver);
	hp.clickMyAccount();
	hp.clickLogin();
			
			
	// Login
	LoginPage lp = new LoginPage(driver);
	lp.setloginEmailAddress(email);
	lp.setloginPassword(pwd);
	lp.clickLogin();
					
			
	// MY Account
	MyAccountPage macc = new MyAccountPage(driver);
	boolean targetPage = macc.isMyAccountPageExists();
	
	/* Data is valid - login success - test passed - logout
	    			 - login failed - test failed
	   
	   Data is invalid - login success - test failed - logout
	   	 			   - login failed -test pass	   
	 */
	
	if(expresult.equalsIgnoreCase("Valid"))
	{
		if(targetPage==true)
		{
			macc.clickLogout();
			Assert.assertTrue(true);
			
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
	
	if(expresult.equalsIgnoreCase("Invalid"))
	{
		if(targetPage==true)
		{
			macc.clickLogout();
			Assert.assertTrue(false);
		}
		else
		{
			Assert.assertTrue(true);
		}
	}
	}
	catch(Exception e)
	{
		Assert.fail();
	}
	
	logger.info("***** Completed TC_003_Login Data Driven Test *****");
	
	
	}
		
}
