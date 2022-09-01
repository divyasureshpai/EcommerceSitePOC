package com.ui.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	public ConfigReader() {

		init_prop();
	}

	private Properties prop = new Properties();

	/**
	 * This method is used to load the properties from config.properties file
	 * 
	 * @return {@link Properties}
	 */

	public Properties init_prop() {

		try {
			FileInputStream src = new FileInputStream("./src/test/resources/com/config/config.properties");
			prop.load(src);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return prop;
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
