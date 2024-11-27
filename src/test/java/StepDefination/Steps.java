package StepDefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

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
        request.log().all();

        response = request.post(endPoint);
        response.then().log().all();
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        response.then().statusCode(statusCode);
    }


    @Given("the API endpoint {string} is available")
    public void theAPIEndpointIsAvailable(String url) {
        RestAssured.baseURI = url;

    }

    @When("I request user list")
    public void iRequestUserList() {
        response = RestAssured
                .given()
                .when()
                .get(); // No query parameters needed for this data
    }

    @Then("I should find user with {string} as {string}")
    public void iShouldFindUserWith(String key, String value) {
        JsonPath jsonPath = response.jsonPath();

        // Extract user data list
        List<Map<String, Object>> users = jsonPath.getList("data");

        // Find user by matching key and value
        Map<String, Object> matchedUser = users.stream()
                .filter(user -> value.equals(user.get(key)))
                .findFirst()
                .orElse(null);

        // Assert user is found and log their details
        assert matchedUser != null : "User with " + key + " = " + value + " not found!";
        System.out.println("User found: " + matchedUser);
    }
}
