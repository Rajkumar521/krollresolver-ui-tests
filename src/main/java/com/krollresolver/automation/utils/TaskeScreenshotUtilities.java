package com.krollresolver.automation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.krollresolver.automation.base.BasePage;

public class TaskeScreenshotUtilities extends BasePage {

	private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + File.separator + "Screenshots"
			+ File.separator;
	private static final String IMAGE_FORMAT = "png";

	public TaskeScreenshotUtilities(WebDriver driver) {
		super(driver);
	}

	public static String takeFullPageScreenshot(WebDriver driver, String screenshotName) {
		try {
			createScreenshotFolderIfNotExists();
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = SCREENSHOT_DIR + screenshotName + "." + IMAGE_FORMAT;
			File dest = new File(path);
			FileUtils.copyFile(src, dest);
			StepLogger.info("Full-page screenshot saved: " + dest.getAbsolutePath());
			return dest.getAbsolutePath();
		} catch (IOException e) {
			StepLogger.fail("Failed to capture full-page screenshot '" + screenshotName + "': " + e.getMessage());
			return null;
		}
	}

	public static String takeElementScreenshot(WebElement element, String screenshotName) {
		try {
			createScreenshotFolderIfNotExists();
			File src = element.getScreenshotAs(OutputType.FILE);
			String path = SCREENSHOT_DIR + screenshotName + "." + IMAGE_FORMAT;
			File dest = new File(path);
			FileUtils.copyFile(src, dest);
			StepLogger.info("Element screenshot saved: " + dest.getAbsolutePath());
			return dest.getAbsolutePath();
		} catch (IOException e) {
			StepLogger.fail("Failed to capture element screenshot '" + screenshotName + "': " + e.getMessage());
			return null;
		}
	}

	public static String capture(WebDriver driver, String screenshotName) {
		String timestampedName = screenshotName + "_" + getTimestamp();
		return takeFullPageScreenshot(driver, timestampedName);
	}

	public static void captureOnFailure(WebDriver driver, String testName, Exception e) {
		String path = capture(driver, testName);
		StepLogger.fail("Test '" + testName + "' failed. Screenshot saved at: " + path + " — " + e.getMessage());
	}

	private static void createScreenshotFolderIfNotExists() {
		File folder = new File(SCREENSHOT_DIR);
		if (!folder.exists()) {
			boolean created = folder.mkdirs();
			if (created) {
				StepLogger.info("Created screenshot directory: " + SCREENSHOT_DIR);
			} else {
				StepLogger.warn("Could not create screenshot directory: " + SCREENSHOT_DIR);
			}
		}
	}

	private static String getTimestamp() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}
}