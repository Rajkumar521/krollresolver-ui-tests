package com.krollresolver.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.krollresolver.automation.base.BasePage;
import com.krollresolver.automation.utils.StepLogger;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// ---------- Locators ----------

	// Headers
	private By testPageTitle = By.xpath("//a[text()='Test Page']");
	private By homeText = By.xpath("//a[contains(text(),'Home ')]");

	// Test 1: Login Section
	private By test1Header = By.xpath("//div[@id='test-1-div']//h1[text()='Test 1']");
	private By emailInput = By.id("inputEmail");
	private By passwordInput = By.id("inputPassword");
	private By loginButton = By.xpath("//button[text()='Sign in']");

	// ---------- Page Actions ----------

	public String getTestPageTitle() {
		String title = getElementText(testPageTitle);
		StepLogger.info("Test Page Title: " + title);
		return title;
	}

	public String getHomeText() {
		String homeTextValue = getElementText(homeText);
		StepLogger.info("Home Text: " + homeTextValue);
		return homeTextValue;
	}

	public String verifyTest1HeaderText() {
		StepLogger.info("Verifying text of Test 1 Header");
		scrollToElement(test1Header);
		return getElementText(test1Header);
	}

	public boolean isEmailFieldDisplayed() {
		boolean status = isElementDisplayed(emailInput);
		StepLogger.info("Email Input Field displayed: " + status);
		return status;
	}

	public void enterEmail(String email) {
		typeText(emailInput, email);
		StepLogger.info("Entered Email: " + email);
	}

	public String getEmailPlaceholder() {
		StepLogger.info("Retrieving placeholder from Email field");
		waitForElementToBeVisible(emailInput);
		focusElement(emailInput);
		WebElement emailField = findElement(emailInput);
		return findElement(emailInput).getAttribute("placeholder");
	}

	public String getPasswordPlaceholder() {
		StepLogger.info("Retrieving placeholder from Password field");
		waitForElementToBeVisible(passwordInput);
		focusElement(passwordInput);
		WebElement passwordField = findElement(passwordInput);
		return findElement(passwordInput).getAttribute("placeholder");
	}

	public String getEmailValidationMessage() {
		StepLogger.info("Retrieving browser-native validation message from Email field");
		waitForElementToBeVisible(emailInput);
		focusElement(emailInput);
		WebElement emailField = findElement(emailInput);
		return findElement(emailInput).getAttribute("validationMessage");
	}

	public String getPasswordValidationMessage() {
		StepLogger.info("Retrieving browser-native validation message from Password field");
		waitForElementToBeVisible(passwordInput);
		focusElement(passwordInput);
		WebElement passwordField = findElement(passwordInput);
		return findElement(passwordInput).getAttribute("validationMessage");
	}

	public boolean isPasswordFieldDisplayed() {
		boolean status = isElementDisplayed(passwordInput);
		StepLogger.info("Password Input Field displayed: " + status);
		return status;
	}

	public String getPasswordFieldAttribute() {
		StepLogger.info("Retrieving Password Input Field type attribute.");
		waitForElementToBeVisible(passwordInput);
		focusElement(passwordInput);
		WebElement passwordField = findElement(passwordInput);
		String attribute = findElement(passwordInput).getAttribute("type");
		StepLogger.info("Password Input Field type attribute: " + attribute);
		return attribute;
	}

	public void enterPassword(String password) {
		typeText(passwordInput, password);
		StepLogger.info("Entered Password.");
	}

	public boolean isLoginButtonDisplayed() {
		boolean status = isElementDisplayed(loginButton);
		StepLogger.info("Login Button displayed: " + status);
		return status;
	}

	public void clickLoginButton() {
		clickButton(loginButton);
		StepLogger.info("Clicked Login Button.");
	}
}