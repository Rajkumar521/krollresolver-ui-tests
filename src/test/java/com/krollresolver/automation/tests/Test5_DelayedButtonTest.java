package com.krollresolver.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.krollresolver.automation.base.BaseTest;
import com.krollresolver.automation.pages.DelayedButtonPage;
import com.krollresolver.automation.utils.StepLogger;

public class Test5_DelayedButtonTest extends BaseTest {

	@Test
	public void verifyDelayedButtonBehavior() {
		// Initialize the page object
		DelayedButtonPage delayedButtonPage = new DelayedButtonPage(getDriver());

		// ---------- Step 0: Verify Home Text ----------
		StepLogger.info("Verifying the Home text");
		String homePageText = delayedButtonPage.getHomeText();
		Assert.assertTrue(homePageText.contains("Home"),
				"Home text does not match expected value. Expected to contain: 'Home', Actual: '" + homePageText + "'");
		StepLogger.pass("Home text verified successfully: " + homePageText);

		// ---------- Step 1: Verify Header ----------
		StepLogger.info("Navigating to Delayed Button page");
		String headerText = delayedButtonPage.getTest5HeaderText();
		Assert.assertEquals(headerText, "Test 5",
				"Header text does not match expected value. Expected: 'Test 5', Actual: '" + headerText + "'");
		StepLogger.pass("Header text verified successfully: " + headerText);

		// ---------- Step 2: Verify Info Message ----------
		StepLogger.info("Verifying the info message before the button appears");
		String infoMessage = delayedButtonPage.getInfoMessageText();
		Assert.assertEquals(infoMessage, "A button will eventually show up here...",
				"Info message does not match expected value. Expected: 'A button will eventually show up here...', Actual: '"
						+ infoMessage + "'");
		StepLogger.pass("Info message verified successfully: " + infoMessage);

		// ---------- Step 3: Wait for Button and Verify State ----------
		StepLogger.info("Waiting for the delayed button to appear");
		Assert.assertTrue(delayedButtonPage.isDelayedButtonDisplayed(), "Delayed button is not displayed.");
		StepLogger.pass("Delayed button is displayed successfully.");

		Assert.assertTrue(delayedButtonPage.isDelayedButtonEnabled(), "Delayed button is not enabled.");
		StepLogger.pass("Delayed button is enabled successfully.");

		// ---------- Step 4: Click the Button ----------
		delayedButtonPage.waitForDelayedButtonAndClick();

		// ---------- Step 5: Verify Success Message ----------
		Assert.assertTrue(delayedButtonPage.isSuccessMessageDisplayed(),
				"Success message is not displayed after clicking the button.");
		String successMessage = delayedButtonPage.getSuccessMessageText();
		Assert.assertTrue(successMessage.contains("You clicked a button!"),
				"Success message does not match expected value. Expected to contain: 'You clicked a button!', Actual: '"
						+ successMessage + "'");
		StepLogger.pass("Success message verified successfully: " + successMessage);

		// ---------- Step 6: Verify Button is Disabled After Click ----------
		boolean isButtonStillEnabled = delayedButtonPage.isDelayedButtonDisabled();
		Assert.assertTrue(isButtonStillEnabled, "Delayed button should be disabled after clicking.");
		StepLogger.pass("Delayed button is disabled after clicking as expected.");
	}
}