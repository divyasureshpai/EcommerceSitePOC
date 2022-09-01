package com.api.util;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClassApi {

	/**
	 * Perform GET request
	 * 
	 * @param url
	 * @param pathParam
	 * @param header
	 * @return the response
	 */
	public Response executeHttpGet(String url, Map<String, String> pathParam, Map<String, String> header) {
		return executeHttpGet(url, pathParam, header, null, false);
	}

	/**
	 * Perform GET request
	 * 
	 * @param url
	 * @param pathParam
	 * @param header
	 * @param token        : if not null, sets this as Bearer Authorization token
	 * @param stopRedirect : if set to true, stops from following the redirect URL
	 * @return the response
	 */
	public Response executeHttpGet(String url, Map<String, String> pathParam, Map<String, String> header, String token,
			boolean stopRedirect) {

		RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON);
		if (!MapUtils.isEmpty(pathParam)) {
			requestSpecification.pathParams(pathParam);
		}
		if (!MapUtils.isEmpty(header)) {
			requestSpecification.headers(header);
		}

		if (token != null) {
			requestSpecification.headers("Authorization", "Bearer " + token);
		}

		if (stopRedirect) {
			requestSpecification.redirects().follow(false);
		}

		return requestSpecification.when().get(url);

	}

	/**
	 * Perform POST request
	 * 
	 * @param url
	 * @param pathParam
	 * @param header
	 * @param body
	 * @param token
	 * @return the response
	 */
	public Response executeHttpPost(String url, Map<String, String> pathParam, Map<String, String> header, Object body,
			String token) {

		RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON);

		if (!MapUtils.isEmpty(pathParam)) {
			requestSpecification.pathParams(pathParam);
		}
		if (!MapUtils.isEmpty(header)) {
			requestSpecification.headers(header);
		}
		if (token != null) {
			requestSpecification.headers("Authorization", "Bearer " + token);
		}
		if (body != null) {
			requestSpecification.body(body);
		}

		return requestSpecification.when().post(url);

	}

	/**
	 * Perform the PATCH request
	 * 
	 * @param url
	 * @param pathParam
	 * @param header
	 * @param body
	 * @param token
	 * @return the response
	 */
	public Response executeHttpPatch(String url, Map<String, String> pathParam, Map<String, String> header, Object body,
			String token) {

		RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON);
		if (!MapUtils.isEmpty(pathParam)) {
			requestSpecification.pathParams(pathParam);
		}
		if (!MapUtils.isEmpty(header)) {
			requestSpecification.headers(header);
		}
		if (token != null) {
			requestSpecification = requestSpecification.headers("Authorization", "Bearer " + token);
		}
		if (body != null) {
			requestSpecification = requestSpecification.body(body);
		}

		return requestSpecification.when().patch(url);

	}

}
