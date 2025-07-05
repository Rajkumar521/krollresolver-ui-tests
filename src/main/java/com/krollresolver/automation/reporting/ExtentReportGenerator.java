package com.krollresolver.automation.reporting;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.krollresolver.automation.utils.StepLogger;

public class ExtentReportGenerator {

	private static ExtentReports extent;
	private static ExtentSparkReporter sparkReporter;
	private static final String REPORT_PATH = System.getProperty("user.dir") + File.separator + "ExtentReports"
			+ File.separator + "index.html";
	public static ExtentReports getInstance() {
		if (extent == null) {
			extent = generateExtent();
		}
		return extent;
	}

	public static ExtentReports generateExtent() {
		try {
			ensureReportDirectoryExists();

			sparkReporter = new ExtentSparkReporter(REPORT_PATH);
			sparkReporter.config().setTimelineEnabled(true);
			sparkReporter.config().setTheme(Theme.DARK);
			sparkReporter.config().setDocumentTitle("Automation Test Report");
			sparkReporter.config().setReportName("UI Test Execution");

			// Load external XML config if present
			sparkReporter.loadXMLConfig(System.getProperty("user.dir") + File.separator + "extent-config.xml");

			// Inject JavaScript to hide empty step-detail boxes
			sparkReporter.config().setJs(
					"document.querySelectorAll('.step-details textarea').forEach(e => { if (!e.value || !e.value.trim()) e.style.display='none'; });");

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);

			StepLogger.info("✅ Extent report initialized at: " + REPORT_PATH);
		} catch (Exception e) {
			StepLogger.fail("❌ Failed to initialize Extent Report: " + e.getMessage());
			throw new RuntimeException("Extent Report initialization failed", e);
		}

		return extent;
	}

	private static void ensureReportDirectoryExists() {
		File reportDir = new File(REPORT_PATH).getParentFile();
		if (!reportDir.exists()) {
			boolean created = reportDir.mkdirs();
			if (created) {
				StepLogger.info("Created report directory: " + reportDir.getAbsolutePath());
			} else {
				StepLogger.warn("Could not create report directory: " + reportDir.getAbsolutePath());
			}
		}
	}
}