package com.api.testcase;

import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.api.context.Context;
import com.api.context.TestContext;
import com.api.domainobjects.LoginRequest;
import com.api.domainobjects.PwdResetRequest;
import com.api.util.BaseClassApi;
import com.api.util.GmailAccess;
import com.ui.util.ConfigReader;

import io.restassured.response.Response;

public class PasswordResetTest {

	private static final String MAIL_QUERY = "from:info@mail.baze.com subject:reset your password label:unread";

	private final TestContext testContext;
	private final BaseClassApi api;
	private final ConfigReader configReader;

	public PasswordResetTest(TestContext testContext, BaseClassApi api, ConfigReader configReader) {
		this.testContext = testContext;
		this.api = api;
		this.configReader = configReader;
	}

	/**
	 * this method retrieves the password reset URL from mail reset request mail
	 * 
	 * @return boolean true the mail was found and the URL was extracted
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public boolean retrieveResetUrlFromMail() throws GeneralSecurityException, IOException {
		String messageBody = GmailAccess.searchMail(MAIL_QUERY);
		if (messageBody != null) {
			String resetUrl = extractResetUrlFromBody(messageBody);
			if (resetUrl != null) {
				testContext.setContext(Context.RESET_URL, resetUrl);
				return true;
			}
		}
		return false;
	}

	/**
	 * This method retrieves the token from the redirect URL received when hitting
	 * the URL from mail reset request mail
	 */
	public void retrieveTokenFromResetApi() {
		String resetUrl = (String) testContext.getContext(Context.RESET_URL);
		Response response = this.api.executeHttpGet(resetUrl, null, null, null, true);
		response.then().statusCode(302).header("Location", notNullValue());
		String redirectUrl = response.header("Location");
		String resetToken = redirectUrl.split("resetToken=")[1];
		testContext.setContext(Context.RESET_TOKEN, resetToken);
	}

	/**
	 * This method hits the password reset end-point to receive the password reset
	 * mail
	 */
	public void requestPasswordReset() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(configReader.getProperty("username"));
		Response response = api.executeHttpPost(configReader.getProperty("pwd.reset.request.url"), null, null,
				loginRequest, null);
		response.then().statusCode(201);
	}

	/**
	 * This method executes the password reset by using the token received from the
	 * redirect URL
	 */
	public void doPasswordReset() {
		PwdResetRequest pwdResetRequest = new PwdResetRequest();
		pwdResetRequest.setResetToken((String) testContext.getContext(Context.RESET_TOKEN));
		pwdResetRequest.setPassword(configReader.getProperty("password"));
		Map<String, String> headers = new HashedMap<>();
		headers.put("Authorization", configReader.getProperty("pwd.reset.basic.auth"));
		Response response = api.executeHttpPatch(configReader.getProperty("pwd.reset.url"), null, headers,
				pwdResetRequest, null);
		System.out.println(response.asPrettyString());
		response.then().statusCode(200);
	}

	private String extractResetUrlFromBody(String messageBody) {
		String startExpr = "Reset password ( ";
		String endExpr = " )";
		int startIndex = messageBody.indexOf(startExpr) + startExpr.length();
		int endIndex = messageBody.indexOf(endExpr, startIndex);
		return messageBody.substring(startIndex, endIndex);
	}
}
