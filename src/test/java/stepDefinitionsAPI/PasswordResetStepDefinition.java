package stepDefinitionsAPI;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Assert;

import com.api.testcase.PasswordResetTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PasswordResetStepDefinition {

	PasswordResetTest passwordResetTest;

	public PasswordResetStepDefinition(PasswordResetTest passwordResetTest) {

		this.passwordResetTest = passwordResetTest;
	}

	@Given("I place the user password reset request")
	public void i_have_the_user_password_reset_end_point_available() {

		passwordResetTest.requestPasswordReset();

	}

	@When("I read the passowrd reset mail")
	public void i_read_the_passowrd_reset_mail() throws GeneralSecurityException, IOException, InterruptedException {
		Thread.sleep(10000);
		int count = 0;
		boolean verify = false;
		while (count < 5 || !verify) {
			verify = passwordResetTest.retrieveResetUrlFromMail();
			count++;
		}
		Assert.assertTrue(verify);

	}

	@When("get the token from the passowrd reset end point in the mail")
	public void get_the_token_from_the_passowrd_reset_end_point_in_the_mail() {

		passwordResetTest.retrieveTokenFromResetApi();

	}

	@Then("the password should be updated on sending the request with new passowrd the the token")
	public void the_password_should_be_updated_on_sending_the_request_with_new_passowrd_the_the_token() {

		passwordResetTest.doPasswordReset();

	}

}
