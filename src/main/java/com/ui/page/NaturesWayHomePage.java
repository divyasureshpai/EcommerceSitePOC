package com.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ui.driver.factory.DriverFactory;
import com.ui.util.BaseClass;

public class NaturesWayHomePage {

	private WebDriver driver;
	private BaseClass baseClass;
	private By shopButton = By.xpath("//a[contains(text(),'shop') and @title='VITAMINS & MINERALS']");
	private By shopText = By.xpath("//p[text()='VITAMINS & MINERALS']");
	private By acceptCookies=By.cssSelector("button[id='onetrust-accept-btn-handler']");

	public NaturesWayHomePage(WebDriver driver) {

		this.driver = driver;
		baseClass = new BaseClass(DriverFactory.getDriver());
	}

	/**
	 * this method clicks on the shop button in the home page
	 * 
	 * @return String: a text value of the shop button section
	 */
	public String clickOnShopButton() {
		
		baseClass.click(acceptCookies);
		String text = baseClass.getTextValue(shopText);
		baseClass.click(shopButton);
		return text;
	}
}
