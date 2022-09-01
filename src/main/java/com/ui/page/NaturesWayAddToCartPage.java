package com.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ui.driver.factory.DriverFactory;
import com.ui.util.BaseClass;
import com.ui.util.TestContext;

public class NaturesWayAddToCartPage {

	private WebDriver driver;
	private BaseClass baseClass;
	private By addToCart = By.xpath("//button[contains(@title,'Add to cart')]");
	private By price = By.xpath("//span[contains(@class,'price-text')]");
	private By cart = By.cssSelector("a[id='mini-cart-icon']");
	private By quantity = By.cssSelector("input[id='displayValue']");

	public NaturesWayAddToCartPage(WebDriver driver) {

		this.driver = driver;
		baseClass = new BaseClass(DriverFactory.getDriver());
	}

	/**
	 * this method perform the add to cart action for the input product
	 * 
	 * @param productName
	 * @param quantity
	 */
	public void addToCart(String productName, String quantity) {

		if (productName.equalsIgnoreCase("5-HTP")) {

			baseClass.enterText(quantity, this.quantity);
		} else if (productName.equalsIgnoreCase("B-50 Complex")) {

			baseClass.enterText(quantity, this.quantity);
		} else if (productName.equalsIgnoreCase("Brain Builder Gummies")) {

			baseClass.enterText(quantity, this.quantity);
		} else {

			System.out.println("cannot add to cart");
		}
		baseClass.click(addToCart);
		String temp = baseClass.getTextValue(price);
		if (temp.contains("$")) {

			temp = temp.split(java.util.regex.Pattern.quote("$"))[1];
		}
		TestContext.addToCart(productName, Integer.parseInt(quantity), Double.parseDouble(temp));
		baseClass.navigateBack();
	}

	/**
	 * this method navigates to the cart from the current page
	 */
	public void navigateToCart() {

		baseClass.click(cart);
	}
}
