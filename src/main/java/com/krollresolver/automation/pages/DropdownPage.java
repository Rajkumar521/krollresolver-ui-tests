package com.krollresolver.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.krollresolver.automation.base.BasePage;
import com.krollresolver.automation.utils.StepLogger;

public class DropdownPage extends BasePage {

	public DropdownPage(WebDriver driver) {
		super(driver);
	}

	// ---------- Locators ----------

	private By homeText = By.xpath("//a[contains(text(),'Home ')]");
	private By test3Header = By.xpath("//div//h1[contains(text(), 'Test 3')]");
	private By defaultDropdown = By.xpath("//button[@id='dropdownMenuButton']");
	private By dropdownElement = By
			.xpath("//div[contains(@class, 'dropdown-menu show')]//a[contains(text(),'Option')]");

	// ---------- Page Metadata ----------

	public String getHomeText() {
		String homeTextValue = getElementText(homeText);
		StepLogger.info("Home Text: " + homeTextValue);
		return homeTextValue;
	}

	public String getTest3HeaderText() {
		StepLogger.info("Retrieving the Test Page Title");
		scrollToElement(test3Header);
		return getElementText(test3Header);
	}

	// ---------- Dropdown Interactions ----------

	public void clickDropdown() {
		if (isElementDisplayed(defaultDropdown)) {
			clickButton(defaultDropdown);
			StepLogger.info("Clicked on the dropdown element.");
		} else {
			StepLogger.warn("Dropdown element is not displayed, cannot click.");
		}
	}

	public String getDefaultDropdownValue() {
		StepLogger.info("Retrieving the default value from the dropdown.");
		return getElementText(defaultDropdown);
	}

	public void selectOptionFromDropdown(String targetValue) {
		StepLogger.info("Selecting value from dropdown: " + targetValue);
		List<WebElement> allOptions = findElements(dropdownElement);
		StepLogger.info("Total options available: " + allOptions.size());

		for (WebElement option : allOptions) {
			String optionText = option.getText();
			StepLogger.info("Checking dropdown option: " + optionText);

			if (optionText.equalsIgnoreCase(targetValue)) {
				StepLogger.info("Selecting dropdown value: " + optionText);
				option.click();
				StepLogger.pass("Dropdown value '" + targetValue + "' selected successfully.");
				return;
			}
		}

		StepLogger.fail("Dropdown value '" + targetValue + "' not found in available options.");
	}
}