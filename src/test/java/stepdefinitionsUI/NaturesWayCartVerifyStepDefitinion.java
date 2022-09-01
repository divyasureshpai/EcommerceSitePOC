package stepdefinitionsUI;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.ui.driver.factory.DriverFactory;
import com.ui.page.NaturesWayAddToCartPage;
import com.ui.page.NaturesWayCartPage;
import com.ui.page.NaturesWayHomePage;
import com.ui.page.NaturesWayProductsPage;
import com.ui.util.ConfigReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NaturesWayCartVerifyStepDefitinion {

	private NaturesWayHomePage homePage = new NaturesWayHomePage(DriverFactory.getDriver());
	private NaturesWayAddToCartPage addToCartPage = new NaturesWayAddToCartPage(DriverFactory.getDriver());
	private NaturesWayProductsPage productsPage = new NaturesWayProductsPage(DriverFactory.getDriver());
	private NaturesWayCartPage cartPage = new NaturesWayCartPage(DriverFactory.getDriver());
	private String[] products;
	private ConfigReader configReader = new ConfigReader();

	@Given("I am at the natures way home page")
	public void i_am_at_the_nature_s_way_home_page() {

		DriverFactory.getDriver().get(configReader.init_prop().getProperty("URL"));
		Assert.assertEquals("The home page title is not equal", "Home | Nature's Way",
				DriverFactory.getDriver().getTitle());
	}

	@Given("I click on the {string} shop option")
	public void i_click_on_the_shop_option(String vitAndMin) {

		String text = homePage.clickOnShopButton();
		Assert.assertEquals("Selected the wrong shop option", vitAndMin, text);

	}

	@When("I perform add to cart action for the products with specific quantity")
	public void i_perfom_add_to_cart_action_for_the_products_with_specific_quantity(
			io.cucumber.datatable.DataTable toCartItems) {

		List<Map<String, String>> toCart = toCartItems.asMaps(String.class, String.class);
		int i = 0;
		products = new String[toCartItems.height() - 1];
		for (Map<String, String> tempToCart : toCart) {

			productsPage.clickViewDetails(tempToCart.get("productname"));
			addToCartPage.addToCart(tempToCart.get("productname"), tempToCart.get("quantity"));
			products[i++] = tempToCart.get("productname");
		}
	}

	@When("I navigate to the cart")
	public void i_navigate_to_the_cart() {

		addToCartPage.navigateToCart();
	}

	@Then("the cart should contain the correct products")
	public void the_cart_should_contain_the_correct_products() {

		Assert.assertTrue(cartPage.verifyProducts(products));
	}

	@Then("the product quantities should be correct")
	public void the_product_quantities_should_be_correct() {

		Assert.assertTrue(cartPage.verifyQuantity());
	}

	@Then("the subtotal should be displayed correctly")
	public void the_subtotal_should_be_displayed_correctly() {

		Assert.assertTrue(cartPage.verifySubtotal(products));
	}

}
