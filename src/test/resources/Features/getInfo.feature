@getUser
Feature: Get a specific user from the API

  Scenario: Find user by first name
    Given the API endpoint "https://reqres.in/api/users?page=1" is available
    When I request user list
    Then I should find user with "first_name" as "George"