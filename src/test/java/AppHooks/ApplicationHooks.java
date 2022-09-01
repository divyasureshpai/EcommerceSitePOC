package AppHooks;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.ui.driver.factory.DriverFactory;
import com.ui.util.ConfigReader;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class ApplicationHooks {

	private WebDriver driver;
	private Properties prop;
	private ConfigReader configReader;
	private DriverFactory driverFactory;

	/**
	 * this method is used to get the property file content
	 */
	@Before(value = "@uitest", order = 0)
	public void getProperty() {

		configReader = new ConfigReader();
		prop = configReader.init_prop();
	}

	/**
	 * this method is used to launch the browser
	 */
	@Before(value = "@uitest", order = 1)
	public void launchBrowser() {

		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);
	}

	/**
	 * this method is used to quit the browser
	 */
	@After(value = "@uitest", order = 0)
	public void quitBrowser() {

		driver.quit();
	}

}
