package com.krollresolver.automation.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GetTableValues {

	public static int[] findCellCoordinates(List<WebElement> rows, String targetValue) {
		try {
			for (int i = 0; i < rows.size(); i++) {
				try {
					List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));

					for (int j = 0; j < cols.size(); j++) {
						try {
							String cellText = cols.get(j).getText().trim();
							if (cellText.equals(targetValue)) {
								StepLogger.info("Found '" + targetValue + "' at row " + i + ", column " + j);
								return new int[] { i, j };
							}
						} catch (Exception e) {
							StepLogger.warn("Error reading cell[" + i + "," + j + "]: " + e.getMessage());
						}
					}
				} catch (Exception e) {
					StepLogger.warn("Error processing row[" + i + "]: " + e.getMessage());
				}
			}
			StepLogger.info("Value '" + targetValue + "' not found in the table.");
			return null;

		} catch (Exception e) {
			StepLogger.fail("Failed to find coordinates for value '" + targetValue + "': " + e.getMessage());
			return null;
		}
	}

}
