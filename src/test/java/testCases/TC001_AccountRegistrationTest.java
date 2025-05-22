package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass{
		
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() throws InterruptedException
	{
		
		logger.info("***** Starting TC001_AccountRegistrationTest ******");
		
		try
		{
		HomePage hp = new HomePage(driver);
		Thread.sleep(3000);
		hp.clickMyAccount();
		logger.info("Clicked My Account link");
		
		hp.clickRegister();
		logger.info("Clicked My Register link");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		logger.info("Providing customer details to register");
		
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString() + "@gmail.com");				// randomly generated the email
				
		regpage.setTelephone(randomNumber());
				
		String password = randomAlphaNumeric();
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message......");
		String confirm = regpage.getConfirmationMsg();
		if (confirm.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed ...");
			logger.debug("Debug logs ...");
			Assert.assertTrue(false);
		}
		}
		
		
		catch(Exception e)
		{
			
			Assert.fail();
		}
		
		logger.info("***** Completed TC001_AccountRegistrationTest ******");
		
	}
	
	
}
