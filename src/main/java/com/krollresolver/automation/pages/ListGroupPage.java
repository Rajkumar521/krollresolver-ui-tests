package com.krollresolver.automation.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.krollresolver.automation.base.BasePage;
import com.krollresolver.automation.utils.StepLogger;

public class ListGroupPage extends BasePage {

    public ListGroupPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Locators ----------

    private By homeText = By.xpath("//a[contains(text(),'Home ')]");
    private By test2Header = By.xpath("//h1[text()='Test 2']");
    private By listGroupItems = By.xpath("//ul[@class='list-group']//li");

    // ---------- Page Metadata ----------

    public String getTest2HeaderText() {
        StepLogger.info("Verifying text of Test 2 Header");
        scrollToElement(test2Header);
        return getElementText(test2Header);
    }

    public String getHomeText() {
        String homeTextValue = getElementText(homeText);
        StepLogger.info("Home Text: " + homeTextValue);
        return homeTextValue;
    }

    // ---------- List Group Utilities ----------

    public List<WebElement> getListGroupItems() {
        StepLogger.info("Retrieving all list group items");
        return findElements(listGroupItems);
    }
    
    public Map<String, String> getListItemData(int index) {
        Map<String, String> data = new HashMap<>();
        List<WebElement> items = getListGroupItems();

        if (index >= items.size()) {
            StepLogger.fail("Index out of bounds for list items: " + index);
            return data;
        }

        WebElement item = items.get(index);
        String fullText = item.getText().trim();
        StepLogger.info("Full text of list item at index " + index + ": " + fullText);

        String badge = "";
        try {
            badge = item.findElement(By.tagName("span")).getText().trim();
            StepLogger.info("Badge text found: " + badge);
        } catch (NoSuchElementException e) {
            StepLogger.warn("No badge found at index " + index);
        }

        String label = fullText.replace(badge, "").trim();
        StepLogger.info("Extracted label: " + label);

        data.put("label", label);
        data.put("badge", badge);
        return data;
    }
}