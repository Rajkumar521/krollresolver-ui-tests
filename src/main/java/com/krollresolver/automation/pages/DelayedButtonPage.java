package com.krollresolver.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.krollresolver.automation.base.BasePage;
import com.krollresolver.automation.utils.StepLogger;

public class DelayedButtonPage extends BasePage {

	public DelayedButtonPage(WebDriver driver) {
		super(driver);
	}

	// ---------- Locators ----------
	
	private By homeText = By.xpath("//a[contains(text(),'Home ')]");
	private By test5Header = By.xpath("//div[@id='test-5-div']//h1");
	private By delayedButton = By.xpath("//div[@id='test-5-div']//button");
	private By successMessage = By.xpath("//div[@id='test-5-div']//div[contains(text(),'You clicked a button')]");
	private By infoMessage = By
			.xpath("//div[@id='test-5-div']//p[contains(text(),'A button will eventually show up here')]");

	// ---------- Page Metadata ----------

	public String getHomeText() {
		String homeTextValue = getElementText(homeText);
		StepLogger.info("Home Text: " + homeTextValue);
		return homeTextValue;
	}

	public String getTest5HeaderText() {
		StepLogger.info("Retrieving the Test 5 header text.");
		scrollToElement(test5Header);
		return getElementText(test5Header);
	}

	public String getInfoMessageText() {
		StepLogger.info("Retrieving the info message before the button appears.");
		return getElementText(infoMessage);
	}

	// ---------- Button State & Interaction ----------

	public boolean isDelayedButtonDisplayed() {
		StepLogger.info("Checking if the delayed button is displayed.");
		return isElementDisplayed(delayedButton);
	}

	public boolean isDelayedButtonEnabled() {
		StepLogger.info("Checking if the delayed button is enabled.");
		return verifyElementEnabledState(delayedButton, true);
	}

	public boolean isDelayedButtonDisabled() {
		StepLogger.info("Checking if the delayed button is disabled.");
		return verifyElementEnabledState(delayedButton, false);
	}

	public void waitForDelayedButtonAndClick() {
		StepLogger.info("Waiting for the delayed button to become visible and clickable.");
		waitForElementToBeVisible(delayedButton);
		clickButton(delayedButton);
		StepLogger.pass("Delayed button clicked successfully.");
	}

	// ---------- Success Message ----------

	public String getSuccessMessageText() {
		StepLogger.info("Retrieving the success message after clicking the button.");
		return getElementText(successMessage);
	}

	public boolean isSuccessMessageDisplayed() {
		StepLogger.info("Checking if the success message is displayed.");
		return isElementDisplayed(successMessage);
	}
}