package StepDefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Steps {
    private String endPoint;
    private Response response;

    @Given("the api endpoint is {string}")
    public void the_api_endpoint_is(String url) {
        this.endPoint = url;
    }

    @When("I send the post request with the following data:")
    public void i_send_the_post_request_with_the_following_data(Map<String, String> userData) {
        RequestSpecification request = RestAssured.given();
        request.header("ContentType", "application/json");
        request.body(userData);

        response = request.post(endPoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        response.then().statusCode(statusCode);
    }
}
