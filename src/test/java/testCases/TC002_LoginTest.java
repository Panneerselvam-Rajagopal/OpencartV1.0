package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
		logger.info("****** Starting TC_002 Login Page Test ******");
		
		try
		{
		// Home Page
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		
		// Login
		LoginPage lp = new LoginPage(driver);
		lp.setloginEmailAddress(prop.getProperty("email"));
		lp.setloginPassword(prop.getProperty("password"));
		lp.clickLogin();
				
		
		// MY Account
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExists();
				
		Assert.assertTrue(targetPage);		//Assert.assertEquals(targetPage, true, "Login Failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("****** Completed TC_002 Login Page Test ******");
		
	}

}
