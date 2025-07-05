package com.krollresolver.automation.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.krollresolver.automation.base.BaseTest;
import com.krollresolver.automation.pages.ListGroupPage;
import com.krollresolver.automation.utils.StepLogger;

public class Test2_ListGroupTest extends BaseTest {

    @Test
    public void testListGroupInTest2() {
        ListGroupPage listGroupPage = new ListGroupPage(getDriver());

        // ---------- Step 1: Verify Home Text ----------
        StepLogger.info("Verifying the Home text");
        String homePageText = listGroupPage.getHomeText();
        Assert.assertTrue(homePageText.contains("Home"),
                "Home text does not match expected value. Expected to contain: 'Home', Actual: '" + homePageText + "'");
        StepLogger.pass("Home text verified successfully: " + homePageText);

        // ---------- Step 2: Verify Test 2 Header ----------
        StepLogger.info("Navigating to List Group page");
        String test2HeaderText = listGroupPage.getTest2HeaderText();
        Assert.assertEquals(test2HeaderText, "Test 2",
                "Test 2 header text does not match expected value. Expected: 'Test 2', Actual: '" + test2HeaderText + "'");
        StepLogger.pass("Test 2 header text verified successfully: " + test2HeaderText);

        // ---------- Step 3: Verify Number of List Items ----------
        StepLogger.info("Verifying the number of list items in Test 2");
        int sizeOfList = listGroupPage.getListGroupItems().size();
        StepLogger.info("Number of list items found: " + sizeOfList);
        Assert.assertEquals(sizeOfList, 3, "Expected 3 list items, but found: " + sizeOfList);
        StepLogger.pass("Number of list items verified successfully: " + sizeOfList);

        // ---------- Step 4: Verify List Item Text ----------
        StepLogger.info("Verifying the text of List Item 2");
        Map<String, String> item = listGroupPage.getListItemData(1);
        Assert.assertEquals(item.get("label"), "List Item 2",
                "List Item 2 text does not match expected value. Expected: 'List Item 2', Actual: '" + item.get("label") + "'");
        StepLogger.pass("List Item 2 text verified successfully");

        // ---------- Step 5: Verify Badge Value ----------
        StepLogger.info("Verifying the badge value of List Item 2");
        Assert.assertEquals(item.get("badge"), "6",
                "Badge value for List Item 2 does not match expected value. Expected: '6', Actual: '" + item.get("badge") + "'");
        StepLogger.pass("Badge value for List Item 2 verified successfully");
    }
}