package com.krollresolver.automation.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.krollresolver.automation.base.BaseTest;
import com.krollresolver.automation.pages.LoginPage;
import com.krollresolver.automation.utils.StepLogger;

public class Test1_LoginTest extends BaseTest {

	LoginPage loginPage;

	@BeforeMethod
	public void setUpPage() {
		loginPage = new LoginPage(getDriver());
	}

	@Test(priority = 1)
	public void verifyLoginFieldsArePresent() {

		// Initialize the test page

		StepLogger.info("Starting Test Case: Test 1 - Sample Login Test");
		StepLogger.info("Navigating to Sample Login Page");

		// Verify the page title
		StepLogger.info("Verifying the Test Page title");
		String pageTitle = loginPage.getTestPageTitle();
		Assert.assertEquals(pageTitle, "Test Page",
				"Page title does not match expected value. Expected: 'Test Page', Actual: '" + pageTitle + "'");
		StepLogger.pass("Test Page title verified successfully: " + pageTitle);

		// Verify Home text
		StepLogger.info("Verifying the Home text");
		String homePageText = loginPage.getHomeText();
		Assert.assertTrue(homePageText.contains("Home"),
				"Home text does not match expected value. Expected to contain: 'Home', Actual: '" + homePageText + "'");
		StepLogger.pass("Home text verified successfully: " + homePageText);

		// Verify Test 1 Header text
		StepLogger.info("Verifying the Test 1 Header text");
		String test1HeaderText = loginPage.verifyTest1HeaderText();
		Assert.assertEquals(test1HeaderText, "Test 1",
				"Test 1 header text does not match expected value. Expected: 'Test 1', Actual: '" + test1HeaderText
						+ "'");
		StepLogger.pass("Test 1 header text verified successfully: " + test1HeaderText);

		// Verify email field is displayed
		StepLogger.info("Verifying if Email field is displayed");
		boolean isEmailFieldDisplayed = loginPage.isEmailFieldDisplayed();
		Assert.assertTrue(isEmailFieldDisplayed, "Email field is not displayed as expected.");
		StepLogger.pass("Email field is displayed as expected.");

		// Verify password field is displayed
		StepLogger.info("Verifying if Password field is displayed");
		boolean isPasswordFieldDisplayed = loginPage.isPasswordFieldDisplayed();
		Assert.assertTrue(isPasswordFieldDisplayed, "Password field is not displayed as expected.");
		StepLogger.pass("Password field is displayed as expected.");

		// Verify login button is displayed
		StepLogger.info("Verifying if Login button is displayed");
		boolean isLoginButtonDisplayed = loginPage.isLoginButtonDisplayed();
		Assert.assertTrue(isLoginButtonDisplayed, "Login button is not displayed as expected.");
		StepLogger.pass("Login button is displayed as expected.");

		// Enter email and password
		StepLogger.info("Entering Email and Password");
		loginPage.enterEmail("test@testing.com"); // Replace with actual email
		loginPage.enterPassword("testing123"); // Replace with actual password
		StepLogger.pass("Email and Password entered successfully.");

		// Click login button
		StepLogger.info("Clicking the Login button");
		loginPage.clickLoginButton();
		StepLogger.pass("Login button clicked successfully.");
	}

	@Test(priority = 2)
	public void verifyInvalidEmailFormatValidation() {
		StepLogger.info("Entering invalid email format");
		loginPage.enterEmail("invalid-email");
		StepLogger.info("Clicking login button with invalid email format");
		loginPage.clickLoginButton();

		String validationMessage = loginPage.getEmailValidationMessage();

		StepLogger.info("Validation message for invalid email: " + validationMessage);
		Assert.assertTrue(validationMessage.toLowerCase().contains("please include an '@'"),
				"Expected email format validation message not shown.");
		StepLogger.pass("Email format validation message verified.");
	}

	@Test(priority = 3)
	public void verifyRequiredPasswordFieldValidationMessage() {
		StepLogger.info("Entering valid email and leaving password empty");

		loginPage.enterEmail("test@testing.com");
		StepLogger.info("Clicking login button without entering password");
		loginPage.clickLoginButton();
		StepLogger.info("Capturing validation message for password field");
		loginPage.getPasswordValidationMessage();
		String validationMessage = loginPage.getPasswordValidationMessage();
		StepLogger.info("Validation message for password field: " + validationMessage);
		Assert.assertEquals(validationMessage, "Please fill out this field.",
				"Validation message for password field does not match expected text.");
		StepLogger.pass("Validation message for password field verified successfully.");
	}

	@Test(priority = 4)
	public void verifyRequiredEmailFieldValidationMessage() {

		StepLogger.info("leaving password empty and Entering email");

		loginPage.enterPassword("password"); // Leaving password empty
		StepLogger.info("Clicking login button without entering password");
		loginPage.clickLoginButton();
		String validationMessage = loginPage.getEmailValidationMessage();

		StepLogger.info("Validation message captured: " + validationMessage);
		Assert.assertEquals(validationMessage, "Please fill out this field.",
				"Validation message does not match expected text.");
		StepLogger.pass("Validation message verified successfully.");
	}

	@Test(priority = 5)
	public void verifyPasswordFieldIsMasked() {
		StepLogger.info("Verifying that the password field is masked.");
		String fieldType = loginPage.getPasswordFieldAttribute();

		StepLogger.info("Password field type: " + fieldType);
		Assert.assertEquals(fieldType, "password", "Password field is not masked.");
		StepLogger.pass("Password field is masked as expected.");
	}

	@Test(priority = 6)
	public void verifyInputFieldPlaceholders() {
		StepLogger.info("Verifying input field placeholders for Email and Password fields.");

		String emailPlaceholder = loginPage.getEmailPlaceholder();
		String passwordPlaceholder = loginPage.getPasswordPlaceholder();

		StepLogger.info("Email placeholder: " + emailPlaceholder);
		StepLogger.info("Password placeholder: " + passwordPlaceholder);

		Assert.assertEquals(emailPlaceholder, "Email address", "Incorrect email placeholder.");
		Assert.assertEquals(passwordPlaceholder, "Password", "Incorrect password placeholder.");
		StepLogger.pass("Input field placeholders verified successfully.");
	}

	// Way 2 - Using data provider to test multiple scenarios for field validation messages
	@Test(dataProvider = "validationScenarios", priority = 7)
	public void verifyFieldValidationMessages(String email, String password, String fieldToCheck,
			String expectedMessage) {
		StepLogger.info("Entering email: " + email + " and password: " + password);
		loginPage.enterEmail(email);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();

		String actualMessage = fieldToCheck.equals("email") ? loginPage.getEmailValidationMessage()
				: loginPage.getPasswordValidationMessage();

		StepLogger.info("Validation message for " + fieldToCheck + " field: " + actualMessage);
		Assert.assertTrue(actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()),
				"Expected validation message: '" + expectedMessage + "', but got: '" + actualMessage + "'");
		StepLogger.pass("Validation message verified successfully for " + fieldToCheck + " field.");
	}

	@DataProvider(name = "validationScenarios")
	public Object[][] validationScenarios() {
		return new Object[][] { { "", "password", "email", "Please fill out this field." },
				{ "invalid-email", "password", "email", "Please include an '@'" },
				{ "test@testing.com", "", "password", "Please fill out this field." }};
	}

}