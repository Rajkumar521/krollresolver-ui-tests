package com.krollresolver.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.krollresolver.automation.base.BasePage;
import com.krollresolver.automation.utils.StepLogger;

public class ButtonStatePage extends BasePage {

	public ButtonStatePage(WebDriver driver) {
		super(driver);
	}

	// ---------- Locators ----------

	private By homeText = By.xpath("//a[contains(text(),'Home ')]");
	private By test4Header = By.xpath("//h1[text()='Test 4']");
	private By buttons = By.xpath("//div[@id='test-4-div']//button");

	// ---------- Page Metadata ----------

	public String getHomeText() {
		String homeTextValue = getElementText(homeText);
		StepLogger.info("Home Text: " + homeTextValue);
		return homeTextValue;
	}

	public String getTest4Header() {
		StepLogger.info("Retrieving the Test 4 header text.");
		scrollToElement(test4Header);
		return getElementText(test4Header);
	}

	// ---------- Button State Validation ----------

	public boolean areButtonStatesAsExpected() {
		StepLogger.info("Verifying button states on the Button State Page...");
		List<WebElement> buttonList = findElements(buttons);
		StepLogger.info("Total buttons found: " + buttonList.size());

		if (buttonList.size() < 2) {
			StepLogger.fail("Expected 2 buttons, but found: " + buttonList.size());
			return false;
		}

		boolean isFirstEnabled = buttonList.get(0).isEnabled();
		boolean isSecondEnabled = buttonList.get(1).isEnabled();

		StepLogger.info("First button is " + (isFirstEnabled ? "enabled" : "disabled"));
		StepLogger.info("Second button is " + (isSecondEnabled ? "enabled" : "disabled"));

		if (isFirstEnabled && !isSecondEnabled) {
			StepLogger.pass("Button states are as expected: first enabled, second disabled.");
			return true;
		} else {
			StepLogger.warn("Unexpected button states.");
			return false;
		}
	}
}