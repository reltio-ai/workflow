package com.reltio.workflow.api.rest;

import java.util.Map;

/**
 * Interface for interaction with Reltio Cloud API
 */
public interface ReltioApi {
	/**
	 *
	 * @param accessToken
	 *            access token from user
	 * @param url
	 *            url to send data
	 * @param requestMethod
	 *            one of "GET", "POST", "PUT" and others http methods
	 * @param requestBody
	 *            body for "POST", "PUT" and others http methods
	 * @return String that should be deserialized to ReltioResponse
	 * @throws ReltioException
	 */
    String invokeApi(String accessToken, String url, String requestMethod, String requestBody) throws ReltioException;

	/**
	 *
	 * @param accessToken	 *            access token from user
	 * @param url			 *            url to send data
	 * @param requestMethod	 *            one of "GET", "POST", "PUT" and others http methods
	 * @param requestBody	 *            body for "POST", "PUT" and others http methods
	 * @return String that should be deserialized to ReltioResponse
	 * @throws ReltioException
	 */
	String invokeApi(String accessToken, String url, String requestMethod, String requestBody, Map<String, String> headers) throws ReltioException;
}
