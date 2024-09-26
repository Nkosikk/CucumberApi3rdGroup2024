@CreateUser
Feature: Create user

  Scenario: send a post request to create a user
    Given the api endpoint is "https://reqres.in/api/users"
    When I send the post request with the following data:
      | name  | job    |
      | Nkosi | tester |
    Then the response status code should be 201
