package com.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ui.driver.factory.DriverFactory;
import com.ui.util.BaseClass;
import com.ui.util.TestContext;

public class NaturesWayCartPage {

	private WebDriver driver;
	private BaseClass baseClass;
	private By subTotalMsg = By.xpath("//p[contains(text(),'Sub')]");

	public NaturesWayCartPage(WebDriver driver) {

		this.driver = driver;
		baseClass = new BaseClass(DriverFactory.getDriver());
	}

	/**
	 * this method verifies the product names in the cart against the products which
	 * were performed Add to Cart action
	 * 
	 * @param productName
	 * @return boolean : true if the product name match happens
	 */
	public boolean verifyProducts(String[] productName) {

		boolean verify = false;
		for (String prod : productName) {

			verify = TestContext.getProduct(prod).getProductName().equals(baseClass.getTextValue(getDynamicPath(prod)));
		}
		return verify;
	}

	/**
	 * this method verifies the total items added in the cart against the products
	 * which were performed Add to Cart action
	 * 
	 * @return boolean true if the cart size match happens against the expected
	 */
	public boolean verifyQuantity() {

		String msgTotal = baseClass.getTextValue(subTotalMsg);
		String countStr = msgTotal.substring(msgTotal.indexOf("(") + 1, msgTotal.indexOf(" item"));
		Integer item = Integer.parseInt(countStr);
		return (item == TestContext.cartSize());
	}

	/**
	 * this method verifies the product total price in the cart against the
	 * accumulated price of the products which were performed Add to Cart action
	 * 
	 * @param productName
	 * @return boolean : true if the price total matches with the expected
	 */
	public boolean verifySubtotal(String[] productName) {

		Double totalPrice = 0.0;
		Double priceTot = 0.0;
		String msgTotal = baseClass.getTextValue(subTotalMsg);
		if (msgTotal.contains("$")) {

			msgTotal = msgTotal.split(java.util.regex.Pattern.quote("$"))[1];
		}
		totalPrice = Double.parseDouble(msgTotal);
		for (String prod : productName) {
			priceTot += TestContext.getProduct(prod).getPrice();
		}
		return (Double.compare(priceTot, totalPrice) == 0);
	}

	private By getDynamicPath(String value) {

		return By.xpath("//span[contains(text(),'" + value + "')]");
	}
}
