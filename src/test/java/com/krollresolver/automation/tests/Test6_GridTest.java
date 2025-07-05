package com.krollresolver.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.krollresolver.automation.base.BaseTest;
import com.krollresolver.automation.pages.GridPage;
import com.krollresolver.automation.utils.StepLogger;

public class Test6_GridTest extends BaseTest {

    @Test
    public void verifyGridDataAndCoordinates() {
        GridPage gridPage = new GridPage(getDriver());

        // ---------- Step 1: Verify Home Text ----------
        StepLogger.info("Verifying the Home text");
        String homePageText = gridPage.getHomeText();
        Assert.assertTrue(homePageText.contains("Home"),
                "Home text does not match expected value. Expected to contain: 'Home', Actual: '" + homePageText + "'");
        StepLogger.pass("Home text verified successfully: " + homePageText);

        // ---------- Step 2: Verify Header ----------
        StepLogger.info("Navigating to the grid page");
        String header = gridPage.getTest6HeaderText();
        Assert.assertEquals(header, "Test 6", "Header text does not match expected value. Expected: 'Test 6', Actual: '" + header + "'");
        StepLogger.pass("Header text matches expected value: " + header);

        // ---------- Step 3: Retrieve Grid Rows ----------
        StepLogger.info("Retrieving all rows from the grid");
        int rowCount = gridPage.getRows().size();
        StepLogger.info("Total rows retrieved: " + rowCount);
        Assert.assertTrue(rowCount > 0, "No rows found in the grid");
        StepLogger.pass("Rows retrieved successfully, count: " + rowCount);

        // ---------- Step 4: Find Coordinates of Specific Value ----------
        String targetValue = "Ventosanzap";
        StepLogger.info("Finding coordinates of the value: " + targetValue);
        int[] coordinates = gridPage.findCoordinates(targetValue);

        Assert.assertNotNull(coordinates, "Coordinates should not be null");
        Assert.assertTrue(coordinates.length == 2, "Coordinates should contain two values (row and column)");
        Assert.assertTrue(coordinates[0] >= 0 && coordinates[1] >= 0,
                "Value '" + targetValue + "' not found in the grid");

        StepLogger.pass("Coordinates found successfully: Row " + coordinates[0] + ", Column " + coordinates[1]);
    }
}