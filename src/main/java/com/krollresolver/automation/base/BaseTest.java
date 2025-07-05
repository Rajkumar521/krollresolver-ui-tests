package com.krollresolver.automation.base;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.krollresolver.automation.config.ReadDataConfig;
import com.krollresolver.automation.utils.StepLogger;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base test class for initializing and tearing down WebDriver sessions.
 */
public class BaseTest {

	protected static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	protected static ReadDataConfig readConfig = ReadDataConfig.getInstance();

	// ---------- Test Lifecycle ----------

	@BeforeClass
	@Parameters("browser")
	public void initialize(String browserparam) {
		String browser = readConfig.getBrowserName();
		String url = readConfig.getApplicationURL();

		try {
			WebDriver driver;

			if (browserparam.equalsIgnoreCase("chrome")) {
				ChromeOptions options = getChromeOptions();
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(options);

			} else if (browserparam.toLowerCase().contains("microsoftedge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();

			} else {
				StepLogger.fail("Browser not supported: " + browserparam);
				throw new RuntimeException("Browser not supported: " + browserparam);
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get(url);

			driverThread.set(driver);

		} catch (Exception e) {
			StepLogger.fail("WebDriver setup failed: " + e.getMessage());
			throw e;
		}
	}

	@AfterClass
	public void tearDown() {
		WebDriver driver = getDriver();
		if (driver != null) {
			StepLogger.info("Closing WebDriver session");
			driver.quit();
			driverThread.remove();
		} else {
			StepLogger.warn("WebDriver already null");
		}
	}

	// ---------- Driver Accessors ----------

	public static WebDriver getDriver() {
		return driverThread.get();
	}

	public static void setDriver(WebDriver driver) {
		driverThread.set(driver);
	}

	private ChromeOptions getChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);
		options.setExperimentalOption("prefs", prefs);
		return options;
	}
}