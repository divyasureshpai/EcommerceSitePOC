package com.api.testcase;

import static org.hamcrest.Matchers.equalTo;

import com.api.context.Context;
import com.api.context.TestContext;
import com.api.domainobjects.LoginRequest;
import com.api.domainobjects.TokenResponse;
import com.api.util.BaseClassApi;
import com.ui.util.ConfigReader;

import io.restassured.response.Response;

public class TestUserAuthentication {

	private final ConfigReader configReader;
	private final BaseClassApi api;
	private final TestContext testContext;

	public TestUserAuthentication(ConfigReader configReader, BaseClassApi api, TestContext testContext) {
		this.configReader = configReader;
		this.api = api;
		this.testContext = testContext;
	}

	/**
	 * Generates access token that is used for user authentication
	 */
	public void generateAccessToken() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(configReader.getProperty("username"));
		loginRequest.setPassword(configReader.getProperty("password"));
		String url = configReader.getProperty("generate_token_url");

		Response response = api.executeHttpPost(url, null, null, loginRequest, null);

		testContext.setContext(Context.TOKEN_RESPONSE, response);
	}

	/**
	 * Validates the response status of the token request
	 */
	public void validateTokenResponseStatus() {
		Response response = (Response) testContext.getContext(Context.TOKEN_RESPONSE);

		response.then().statusCode(200);
	}

	/**
	 * Gives the user information for a given email & password
	 */
	public void getUserInfo() {
		String url = configReader.getProperty("get_user_info_url");
		Response tokenResponseObj = (Response) testContext.getContext(Context.TOKEN_RESPONSE);
		TokenResponse tokenResponse = tokenResponseObj.as(TokenResponse.class);

		Response response = api.executeHttpGet(url, null, null, tokenResponse.getAccess(), false);
		testContext.setContext(Context.USER_INFO_RESPONSE, response);
	}

	public void validateUserInfoResponse() {
		Response response = (Response) testContext.getContext(Context.USER_INFO_RESPONSE);

		response.then().statusCode(200).body("email", equalTo("divya.enezhuth@gmail.com"), "fullName",
				equalTo("Divya SURESH PAI"), "firstname", equalTo("Divya"), "lastname", equalTo("SURESH PAI"));
	}
}
