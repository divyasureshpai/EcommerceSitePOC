package com.ui.util;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

	private static Map<String, SelectedProduct> productsMap = new HashMap<>();

	/**
	 * this method is used to return the product information based on the product
	 * name
	 * 
	 * @param name
	 * @return link SelectedProduct
	 */
	public static SelectedProduct getProduct(String name) {
		return productsMap.get(name);
	}

	/**
	 * this method is used store the product information added to the cart based on
	 * product name
	 * 
	 * @param name
	 * @param qty
	 * @param price
	 */
	public static void addToCart(String name, Integer qty, Double price) {
		SelectedProduct product;
		if (productsMap.containsKey(name)) {
			product = productsMap.get(name);
		} else {
			product = new SelectedProduct();
			product.setProductName(name);
			productsMap.put(name, product);
		}
		product.setQuantity(qty);
		product.setPrice(price * qty);
	}

	/**
	 * this method returns the total elements added in the cart
	 * 
	 * @return int : cart size
	 */
	public static int cartSize() {

		return productsMap.size();
	}
}
