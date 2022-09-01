package stepDefinitionsAPI;

import com.api.testcase.TestUserAuthentication;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AuthenticationConfirmStepDefinition {

	TestUserAuthentication testUserAuthentication;

	public AuthenticationConfirmStepDefinition(TestUserAuthentication testUserAuthentication) {

		this.testUserAuthentication = testUserAuthentication;
	}

	@Given("the Authentication end point is available")
	public void the_authentication_end_point_is_available() {
		testUserAuthentication.generateAccessToken();
	}

	@Given("I generate the Authentication token respose")
	public void i_generate_the_authentication_token_respose() {
		testUserAuthentication.validateTokenResponseStatus();
	}

	@When("I use the access token to login the User end point")
	public void i_use_the_access_token_to_login_the_user_end_point() {
		testUserAuthentication.getUserInfo();
	}

	@Then("I should get success response")
	@Then("I should be able to verify the respose payload")
	public void i_should_be_able_to_verify_the_respose_payload() {

	}

}
