package com.example.api.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RestUtil {

    // --- HTTP Methods ---

    public static Response get(RequestSpecification spec, String endpoint) {
        return given().spec(spec).get(endpoint);
    }

    public static Response getWithParams(RequestSpecification spec, String endpoint, Map<String, String> params) {
        return given().spec(spec).queryParams(params).get(endpoint);
    }

    public static Response post(RequestSpecification spec, String endpoint, Object body) {
        return given().spec(spec).body(body).post(endpoint);
    }

    public static Response put(RequestSpecification spec, String endpoint, Object body) {
        return given().spec(spec).body(body).put(endpoint);
    }

    public static Response patch(RequestSpecification spec, String endpoint, Object body) {
        return given().spec(spec).body(body).patch(endpoint);
    }

    public static Response delete(RequestSpecification spec, String endpoint) {
        return given().spec(spec).delete(endpoint);
    }

    // --- Hamcrest-Based Verifications ---

    public static void verifyStatusCode(Response response, int expectedStatus) {
        assertThat("Unexpected status code", response.getStatusCode(), is(expectedStatus));
    }

    public static void verifyResponseContains(Response response, String expectedText) {
        assertThat("Expected response to contain text", response.asString(), containsString(expectedText));
    }

    public static void verifyJsonField(Response response, String jsonPath, Object expectedValue) {
        Object actual = response.jsonPath().get(jsonPath);
        assertThat("Mismatch in JSON field: " + jsonPath, actual, equalTo(expectedValue));
    }

    public static void verifyJsonFieldExists(Response response, String jsonPath) {
        assertThat("Expected JSON field to exist: " + jsonPath,
                response.jsonPath().get(jsonPath), is(notNullValue()));
    }

}

