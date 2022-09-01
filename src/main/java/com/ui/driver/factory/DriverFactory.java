package com.ui.driver.factory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * Initiate the threadlocal driver based on the given browser
	 * 
	 * @param browser
	 * @return WebDriver
	 */
	public WebDriver init_driver(String browser) {

		if (browser.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver());
		} else if (browser.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			tlDriver.set(new EdgeDriver());
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return getDriver();
	}

	/**
	 * This method gets the threadlocal driver
	 * 
	 * @return {@link WebDriver}
	 */
	public static synchronized WebDriver getDriver() {

		return tlDriver.get();
	}

}
