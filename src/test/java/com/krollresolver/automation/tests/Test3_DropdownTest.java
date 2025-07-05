package com.krollresolver.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.krollresolver.automation.base.BaseTest;
import com.krollresolver.automation.pages.DropdownPage;
import com.krollresolver.automation.utils.StepLogger;

public class Test3_DropdownTest extends BaseTest {

    @Test
    public void testDropdownSelection() {
        // Initialize the page object for the dropdown page
        DropdownPage dropdownPage = new DropdownPage(getDriver());

        // ---------- Step 1: Verify Home Text ----------
        StepLogger.info("Verifying the Home text");
        String homePageText = dropdownPage.getHomeText();
        Assert.assertTrue(homePageText.contains("Home"),
                "Home text does not match expected value. Expected to contain: 'Home', Actual: '" + homePageText + "'");
        StepLogger.pass("Home text verified successfully: " + homePageText);

        // ---------- Step 2: Verify Header Text ----------
        StepLogger.info("Navigating to Dropdown page");
        String headerText = dropdownPage.getTest3HeaderText();
        Assert.assertEquals(headerText, "Test 3",
                "Header text does not match expected value. Expected: 'Test 3', Actual: '" + headerText + "'");
        StepLogger.pass("Header text verified successfully: " + headerText);

        // ---------- Step 3: Verify Default Selected Option ----------
        StepLogger.info("Verifying default selected option in dropdown");
        String defaultSelectedOption = dropdownPage.getDefaultDropdownValue();
        Assert.assertEquals(defaultSelectedOption, "Option 1",
                "Default selected option does not match expected value. Expected: 'Option 1', Actual: '" + defaultSelectedOption + "'");
        StepLogger.pass("Default selected option verified successfully: " + defaultSelectedOption);

        // ---------- Step 4: Select an Option ----------
        StepLogger.info("Selecting an option from the dropdown");
        String optionToSelect = "Option 3";
        dropdownPage.clickDropdown();
        dropdownPage.selectOptionFromDropdown(optionToSelect);

        // ---------- Step 5: Verify Selected Option ----------
        StepLogger.info("Verifying selected option in dropdown");
        String selectedOption = dropdownPage.getDefaultDropdownValue();
        Assert.assertEquals(selectedOption, optionToSelect,
                "Selected option does not match expected value. Expected: '" + optionToSelect + "', Actual: '" + selectedOption + "'");
        StepLogger.pass("Selected option verified successfully: " + selectedOption);
    }
}