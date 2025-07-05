package com.krollresolver.automation.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Timeouts{

	private WebDriver driver;
	private WebDriverWait wait;
	private final int defaultTimeout = 15;
	

	public Timeouts(WebDriver driver) {
		 if (driver == null) throw new IllegalArgumentException("WebDriver cannot be null");
	        this.driver = driver;
	}

	public void setImplicitWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
	}

	public void setPageLoadTimeout(int seconds) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(seconds));
	}

	public void setScriptTimeout(int seconds) {
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(seconds));
	}

	public WebElement waitUntilVisible(By locator, int timeoutInSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitUntilClickable(By locator, int timeoutInSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void customSleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
