package com.krollresolver.automation.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.krollresolver.automation.utils.StepLogger;
import com.krollresolver.automation.utils.Timeouts;

public class BasePage extends Page {

	private final Timeouts timeouts;
	private final int seconds = 15;

	public BasePage(WebDriver driver) {
		super(driver);
		this.timeouts = new Timeouts(driver);
	}

	@Override
	public String getElementText(By locator) {
		try {
			String text = timeouts.waitUntilVisible(locator, seconds).getText();
			StepLogger.info("Read text from element " + locator + ": " + text);
			return text;
		} catch (Exception e) {
			StepLogger.fail("Failed to read text from element: " + locator + " — " + e.getMessage());
			throw e;
		}
	}

	@Override
	public String getPageTitle() {
		try {
			String title = driver.getTitle();
			StepLogger.info("Retrieved the page title: " + title);
			return title;
		} catch (Exception e) {
			StepLogger.fail("Failed to retrieve page title — " + e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean isElementDisplayed(By locator) {
		try {
			WebElement element = timeouts.waitUntilVisible(locator, seconds);
			boolean isDisplayed = element.isDisplayed();
			StepLogger.info("Element " + locator + " is displayed: " + isDisplayed);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			StepLogger.warn("Element not found: " + locator);
			return false;
		} catch (Exception e) {
			StepLogger.fail("Error checking if element is displayed: " + locator + " — " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean isElementEnabled(By locator) {
		try {
			WebElement element = timeouts.waitUntilVisible(locator, seconds);
			boolean isEnabled = element.isEnabled();
			StepLogger.info("Element " + locator + " is enabled: " + isEnabled);
			return isEnabled;
		} catch (NoSuchElementException e) {
			StepLogger.warn("Element not found: " + locator);
			return false;
		} catch (Exception e) {
			StepLogger.fail("Error checking if element is enabled: " + locator + " — " + e.getMessage());
			return false;
		}
	}

	@Override
	public WebElement findElement(By locator) {
		try {
			return driver.findElement(locator);
		} catch (NoSuchElementException e) {
			StepLogger.warn("Element not found: " + locator);
			throw new NoSuchElementException("Could not find element: " + locator, e);
		}
	}

	@Override
	public List<WebElement> findElements(By locator) {
		try {
			List<WebElement> elements = driver.findElements(locator);
			if (elements == null || elements.isEmpty()) {
				StepLogger.warn("No elements found for: " + locator);
				throw new NoSuchElementException("No elements found for locator: " + locator);
			}
			return elements;
		} catch (Exception e) {
			StepLogger.warn("Error retrieving elements: " + locator + " — " + e.getMessage());
			throw new NoSuchElementException("Error retrieving elements: " + locator, e);
		}
	}

	@Override
	public void waitForElementToBeVisible(By locator) {
		try {
			StepLogger.info("Waiting for element to be visible: " + locator);
			timeouts.waitUntilVisible(locator, seconds);
		} catch (Exception e) {
			StepLogger.fail("Timeout waiting for element: " + locator + " — " + e.getMessage());
			throw new RuntimeException("Timeout waiting for element: " + locator, e);
		}
	}

	@Override
	public void waitForTitleElementVisible(By locator) {
		try {
			StepLogger.info("Waiting for page title element: " + locator);
			timeouts.waitUntilVisible(locator, seconds);
		} catch (Exception e) {
			StepLogger.warn("Timeout waiting for page title element: " + locator + " — " + e.getMessage());
			throw new RuntimeException("Timeout waiting for page title element: " + locator, e);
		}
	}

	@Override
	public void typeText(By locator, String keys) {
		try {
			timeouts.waitUntilVisible(locator, seconds).sendKeys(keys);
			StepLogger.info("Typed '" + keys + "' into element: " + locator);
		} catch (Exception e) {
			StepLogger.fail("Failed to type '" + keys + "' into element: " + locator + " — " + e.getMessage());
			throw new RuntimeException("Failed to type into element: " + locator, e);
		}
	}

	@Override
	public void clickButton(By locator) {
		try {
			timeouts.waitUntilClickable(locator, seconds).click();
			StepLogger.info("Clicked element: " + locator);
		} catch (Exception e) {
			StepLogger.fail("Failed to click element: " + locator + " — " + e.getMessage());
			throw e;
		}
	}

	// Custom method to verify if an element is enabled or disabled
	public boolean verifyElementEnabledState(By locator, boolean expectedEnabled) {
		try {
			WebElement element = findElement(locator);
			boolean actualEnabled = element.isEnabled();

			StepLogger.info("Element [" + locator + "] is " + (actualEnabled ? "enabled" : "disabled") + " (expected: "
					+ (expectedEnabled ? "enabled" : "disabled") + ")");

			if (actualEnabled == expectedEnabled) {
				StepLogger.pass("Element state matches expected: " + (expectedEnabled ? "enabled" : "disabled"));
				return true;
			} else {
				StepLogger.warn("Element state mismatch. Actual: " + (actualEnabled ? "enabled" : "disabled"));
				return false;
			}
		} catch (NoSuchElementException e) {
			StepLogger.fail("Element not found: " + locator);
			return false;
		}
	}

	public void scrollToElement(By locator) {
		WebElement element = findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	}
	
	public void focusElement(By locator) {
	    WebElement element = findElement(locator);
	    ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
	}


	// Custom method to get the value next to a specific text in a list
	public String getValueNextToText(By listLocator, String targetText) {
		List<WebElement> listItems = findElements(listLocator);

		for (WebElement item : listItems) {
			String itemText = item.getText().trim();
			if (itemText.contains(targetText)) {
				try {
					WebElement span = item.findElement(By.tagName("span"));
					String value = span.getText().trim();
					StepLogger.info("Found value '" + value + "' next to text: " + targetText);
					return value;
				} catch (NoSuchElementException e) {
					StepLogger.warn("No span found next to: " + targetText);
					throw new RuntimeException("No span found next to: " + targetText, e);
				}
			}
		}

		StepLogger.warn("No list item found with text: " + targetText);
		return null;
	}
}