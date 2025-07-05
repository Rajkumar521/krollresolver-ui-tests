package com.krollresolver.automation.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.krollresolver.automation.utils.StepLogger;

public abstract class Page {

	protected WebDriver driver;
	public static WebDriverWait wait;

	public Page(WebDriver driver) {
		this.driver = driver;

		if (driver instanceof RemoteWebDriver) {
			String browser = ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
		} else {
			StepLogger.info("WebDriver initialized and decorated: " + driver.getClass().getSimpleName());
		}
	}

	// ---------- Element Interactions ----------

	public abstract void clickButton(By locator);

	public abstract void typeText(By locator, String keys);

	// ---------- Element State Checks ----------

	public abstract boolean isElementDisplayed(By locator);

	public abstract boolean isElementEnabled(By locator);

	// ---------- Element Retrieval ----------

	public abstract String getElementText(By locator);

	public abstract WebElement findElement(By locator);

	public abstract List<WebElement> findElements(By locator);

	// ---------- Wait Utilities ----------

	public abstract void waitForElementToBeVisible(By locator);

	public abstract void waitForTitleElementVisible(By locator);

	// ---------- Page Metadata ----------

	public abstract String getPageTitle();
}