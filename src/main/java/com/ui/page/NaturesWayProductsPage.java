package com.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ui.driver.factory.DriverFactory;
import com.ui.util.BaseClass;

public class NaturesWayProductsPage {

	private WebDriver driver;
	private BaseClass baseClass;
	private By viewDetails_htp = By.xpath("//a[contains(@title,'5-HTP View')]");
	private By viewDetails_b50 = By.xpath("//a[contains(@title,'B-50 Complex View')]");
	private By viewDetails_brain = By.xpath("//a[contains(@title,'Builder Gummies View')]");

	public NaturesWayProductsPage(WebDriver driver) {

		this.driver = driver;
		baseClass = new BaseClass(DriverFactory.getDriver());
	}

	/**
	 * this method clicks on view details button based on the input product name
	 * 
	 * @param productName
	 */
	public void clickViewDetails(String productName) {

		if (productName.equalsIgnoreCase("5-HTP")) {

			baseClass.click(viewDetails_htp);
		} else if (productName.equalsIgnoreCase("B-50 Complex")) {

			baseClass.click(viewDetails_b50);
		} else if (productName.equalsIgnoreCase("Brain Builder Gummies")) {

			baseClass.click(viewDetails_brain);
		}

	}
}
