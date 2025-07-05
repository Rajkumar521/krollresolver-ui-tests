package com.krollresolver.automation.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.krollresolver.automation.base.BaseTest;
import com.krollresolver.automation.reporting.ExtentReportGenerator;
import com.krollresolver.automation.reporting.ExtentTestManager;
import com.krollresolver.automation.utils.StepLogger;
import com.krollresolver.automation.utils.TaskeScreenshotUtilities;

public class TestListeners implements ITestListener {

	private static final ExtentReports extent = ExtentReportGenerator.generateExtent();
	private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
		StepLogger.info("📦 Test execution started: " + context.getName());

		// Set system info once per suite
		String browser = context.getCurrentXmlTest().getParameter("browser");
		String os = System.getProperty("os.name");
		String javaVersion = System.getProperty("java.version");

		extent.setSystemInfo("Browser", browser);
		extent.setSystemInfo("OS", os);
		extent.setSystemInfo("Java Version", javaVersion);
		extent.setSystemInfo("SDET", "Raj");
		extent.setSystemInfo("Environment", "QA");
	}

	@Override
	public void onTestStart(ITestResult result) {
		String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
		String os = System.getProperty("os.name");

		ExtentTest test = extent.createTest(result.getMethod().getMethodName() + " - " + browser).assignAuthor("Raj")
				.assignCategory("Smoke-Test", os).assignDevice(browser);

		extentTest.set(test);
		ExtentTestManager.setTest(result.getMethod().getMethodName(), test);

		StepLogger.info("Test started: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		WebDriver driver = BaseTest.getDriver();
		String methodName = result.getMethod().getMethodName();

		String screenshotPath = TaskeScreenshotUtilities.takeFullPageScreenshot(driver, methodName);

		extentTest.get().log(Status.PASS, "Test passed successfully");
		StepLogger.passWithScreenshotButton(extentTest.get(), "Test passed: " + methodName, screenshotPath);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		WebDriver driver = BaseTest.getDriver();
		String methodName = result.getMethod().getMethodName();

		String screenshotPath = TaskeScreenshotUtilities.capture(driver, methodName);

		extentTest.get().fail(result.getThrowable()).addScreenCaptureFromPath(screenshotPath);
		StepLogger.fail("Test failed: " + methodName + " — Exception: " + result.getThrowable().getMessage());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();

		extentTest.get().log(Status.SKIP, "Test was skipped");
		StepLogger.warn("Test skipped: " + methodName);
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		StepLogger.info("Test execution finished: " + context.getName());
	}
}