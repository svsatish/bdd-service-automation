package com.example.api.stepdefinitions;

import com.example.api.models.user.UserRequest;
import com.example.api.utils.ConfigManager;
import com.example.api.utils.RestUtil;
import config.RequestConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateUserSteps {

    private Response response;

    @Given("the Petstore API is available")
    public void the_petstore_api_is_available() {
        ConfigManager.get("petstore.base.url");
    }

    @When("I create a new user with the following details:")
    public void i_create_a_new_user_with_the_following_details(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> userData = dataTable.asMaps().get(0);

        UserRequest user = new UserRequest(
                userData.get("firstName"),
                userData.get("lastName"),
                userData.get("password"),
                Integer.parseInt(userData.get("userStatus")),
                userData.get("phone"),
                Integer.parseInt(userData.get("id")),
                userData.get("email"),
                userData.get("username")
        );

        response = RestUtil.post(RequestConfig.getPetstoreSpec("12345678"), "/user", user);
    }

    @Then("the user should be created successfully with:")
    public void the_user_should_be_created_successfully_with(DataTable dataTable) {
        Map<String, String> expected = dataTable.asMaps().get(0);

        response.then().statusCode(Integer.parseInt(expected.get("code")));
        assertThat(response.jsonPath().getString("type"), is(expected.get("type")));
        assertThat(response.jsonPath().getString("message"), is(expected.get("message")));
    }

}

