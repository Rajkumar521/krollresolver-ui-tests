package com.krollresolver.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.krollresolver.automation.base.BasePage;
import com.krollresolver.automation.utils.GetTableValues;
import com.krollresolver.automation.utils.StepLogger;

public class GridPage extends BasePage {

    public GridPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Locators ----------

    private By test6Header = By.xpath("//h1[text()='Test 6']");
    private By bodyRows = By.xpath("//div[@id='test-6-div']//table//tbody//tr");
    private By homeText = By.xpath("//a[contains(text(),'Home ')]");

    // ---------- Page Metadata ----------

    public String getHomeText() {
        String homeTextValue = getElementText(homeText);
        StepLogger.info("Home Text: " + homeTextValue);
        return homeTextValue;
    }

    public String getTest6HeaderText() {
        StepLogger.info("Retrieving header text from the grid page");
        scrollToElement(test6Header);
        return getElementText(test6Header);
    }

    // ---------- Grid Utilities ----------
   
    public List<WebElement> getRows() {
        StepLogger.info("Retrieving all rows from the grid");
        return findElements(bodyRows);
    }

    public int[] findCoordinates(String cellValue) {
        StepLogger.info("Searching for cell coordinates containing value: " + cellValue);
        return GetTableValues.findCellCoordinates(getRows(), cellValue);
    }
}