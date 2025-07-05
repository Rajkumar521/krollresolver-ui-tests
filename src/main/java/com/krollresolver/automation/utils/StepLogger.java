package com.krollresolver.automation.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.krollresolver.automation.reporting.ExtentTestManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class StepLogger {

	private static final Logger logger = LogManager.getLogger(StepLogger.class);

	private static StackTraceElement getCaller() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return (stack.length > 4) ? stack[4] : null;
	}

	private static String formatCallerInfo() {
		StackTraceElement caller = getCaller();
		return (caller != null) ? String.format("[%s:%d] ", caller.getClassName(), caller.getLineNumber()) : "";
	}

	public static void info(String message) {
		ExtentTest test = ExtentTestManager.getCurrentTest();
		if (test != null)
			test.info(message);
		logger.info(formatCallerInfo() + message);
	}

	public static void pass(String message) {
		ExtentTest test = ExtentTestManager.getCurrentTest();
		if (test != null)
			test.pass("✅ " + message);
		logger.info(formatCallerInfo() + "[PASS] " + message);
	}

	public static void fail(String message) {
		ExtentTest test = ExtentTestManager.getCurrentTest();
		if (test != null)
			test.fail("❌ " + message);
		logger.error(formatCallerInfo() + "[FAIL] " + message);
	}

	public static void warn(String message) {
		ExtentTest test = ExtentTestManager.getCurrentTest();
		if (test != null)
			test.warning("⚠️ " + message);
		logger.warn(formatCallerInfo() + "[WARN] " + message);
	}

	public static void log(Status status, String message) {
		ExtentTest test = ExtentTestManager.getCurrentTest();
		if (test != null)
			test.log(status, message);
		logger.info(formatCallerInfo() + "[" + status + "] " + message);
	}

	// ✅ Log passed step with screenshot button
	public static void passStepWithScreenshot(WebDriver driver, String message) {
		ExtentTest test = ExtentTestManager.getCurrentTest();
		if (test == null) {
			logger.warn("⚠️ ExtentTest not initialized for passStepWithScreenshot");
			return;
		}

		try {
			String screenshotPath = TaskeScreenshotUtilities.takeFullPageScreenshot(driver,
					message.replaceAll("\\s+", "_"));
			String htmlButton = "<a href='" + screenshotPath + "' target='_blank' "
					+ "style='display:inline-block;padding:6px 12px;margin-top:5px;font-size:13px;"
					+ "color:#fff;background-color:#28a745;border:none;border-radius:4px;text-decoration:none;'>Click here to see screenshot</a>";

			test.pass("✅ " + message + "<br>" + htmlButton);
			logger.info(formatCallerInfo() + "[PASS] " + message + " (screenshot attached)");
		} catch (Exception e) {
			test.pass("✅ " + message + " (⚠️ Screenshot not available)");
			logger.warn(formatCallerInfo() + "[PASS] " + message + " (screenshot failed: " + e.getMessage() + ")");
		}
	}

	// 🔁 Still available if you want to manually pass with a screenshot path
	public static void passWithScreenshotButton(ExtentTest test, String message, String relativeScreenshotPath) {
		String htmlButton = "<a href='" + relativeScreenshotPath + "' target='_blank' "
			    + "style='display:inline-block;padding:6px 12px;margin-top:6px;font-size:13px;"
			    + "color:#fff;background-color:#4682B4;border:none;border-radius:20px;"
			    + "text-decoration:none;font-style:italic;font-weight:500;"
			    + "box-shadow:0 2px 6px rgba(0,0,0,0.2);transition:all 0.3s ease;'>"
			    + "Click here to see screenshot</a>";

		test.pass(message + "<br>" + htmlButton);
	}
}