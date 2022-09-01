package com.ui.util;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class BaseClass {

	WebDriver driver;
	Select select;
	Alert alert;

	public BaseClass(WebDriver driver) {

		this.driver = driver;
	}

	/**
	 * this method is used to click on the WebElement
	 * 
	 * @param locValue
	 */
	public void click(By locValue) {
		try {
			driver.findElement(locValue).click();
		} catch (Exception e) {
			System.err.println("Unable to click on the webelement:" + locValue);
		}
	}

	/**
	 * this method is used to give input to the WebElement
	 * 
	 * @param text
	 * @param locValue
	 */
	public void enterText(String text, By locValue) {
		try {
			driver.findElement(locValue).clear();
			driver.findElement(locValue).sendKeys(text);
		} catch (Exception e) {
			System.err.println("Unable enter text on the webelement:" + locValue);
		}
	}

	/**
	 * this method is used to navigate back to previous page
	 * 
	 */
	public void navigateBack() {

		try {
			driver.navigate().back();
		} catch (Exception e) {

			System.err.println("Unable to navigate back");
		}
	}

	/**
	 * this method is used to return text value corresponding to WebElement
	 * 
	 * @param locValue
	 * @return String : the text of the WebElement
	 */
	public String getTextValue(By locValue) {

		String temp = null;
		try {
			temp = driver.findElement(locValue).getText();
		} catch (Exception e) {

			System.err.println("Unable get the text value");
		}
		return temp;
	}

}
