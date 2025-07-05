package com.krollresolver.automation.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import com.krollresolver.automation.utils.StepLogger;

public class ReadDataConfig {

	private final Properties properties = new Properties();

	public ReadDataConfig() {
		try {

			File configFile = new File("./Configurations/config.properties");
			StepLogger.info("Loading configuration from: " + configFile.getAbsolutePath());

			FileInputStream fis = new FileInputStream(configFile);
			properties.load(fis);
			fis.close();

			StepLogger.info("Configuration loaded successfully.");
		} catch (IOException e) {
			StepLogger.fail("Failed to load configuration file: " + e.getMessage());
			throw new RuntimeException("Configuration loading failed", e);
		}
	}

	public String getApplicationURL() {
		return properties.getProperty("baseUrl");
	}

	public String getUsername() {
		return properties.getProperty("username");
	}

	public String getPassword() {
		return properties.getProperty("password");
	}

	public String getBrowserName() {
		return properties.getProperty("browser");
	}

	public String getBrowserPath() {
		return properties.getProperty("chromepath");
	}
}