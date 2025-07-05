package com.krollresolver.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.krollresolver.automation.base.BaseTest;
import com.krollresolver.automation.pages.ButtonStatePage;
import com.krollresolver.automation.utils.StepLogger;

public class Test4_ButtonStateTest extends BaseTest {

	@Test
	public void verifyButtonStatesOnTest4Page() {
		// Initialize the page object
		ButtonStatePage buttonStatePage = new ButtonStatePage(getDriver());

		// ---------- Step 1: Verify Home Text ----------
		StepLogger.info("Verifying the Home text");
		String homePageText = buttonStatePage.getHomeText();
		Assert.assertTrue(homePageText.contains("Home"),
				"Home text does not match expected value. Expected to contain: 'Home', Actual: '" + homePageText + "'");
		StepLogger.pass("Home text verified successfully: " + homePageText);

		// ---------- Step 2: Verify Header ----------
		StepLogger.info("Verifying the Test 4 header");
		String headerText = buttonStatePage.getTest4Header();
		Assert.assertEquals(headerText, "Test 4",
				"Header text does not match expected value. Expected: 'Test 4', Actual: '" + headerText + "'");
		StepLogger.pass("Header text verified successfully: " + headerText);

		// ---------- Step 3: Verify Button States ----------
		StepLogger.info("Verifying initial state of the buttons");
		boolean buttonStatesCorrect = buttonStatePage.areButtonStatesAsExpected();
		Assert.assertTrue(buttonStatesCorrect,
				"Button states are not as expected: first should be enabled, second disabled.");
		StepLogger.pass("Button states verified successfully: first enabled, second disabled.");
	}
}