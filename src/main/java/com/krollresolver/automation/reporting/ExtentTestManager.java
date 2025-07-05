package com.krollresolver.automation.reporting;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static final Map<String, ExtentTest> testMap = new HashMap<>();

    public static synchronized void setTest(String testName, ExtentTest test) {
        testThread.set(test);
        testMap.put(testName, test);
    }

    public static ExtentTest getCurrentTest() {
        return testThread.get();
    }

    public static ExtentTest getTestByName(String testName) {
        return testMap.get(testName);
    }
}