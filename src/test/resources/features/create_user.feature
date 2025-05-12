Feature: Create a new user in Petstore

  As an API tester
  I want to create a new user using the Petstore API
  So that I can verify user creation functionality

  Scenario: Create a user with valid data
    Given the Petstore API is available
    When I create a new user with the following details:
      | id    | username | firstName | lastName | email             | password | phone       | userStatus |
      | 1001  | johndoe  | John      | Doe      | john@example.com  | pass123  | 1234567890  | 1          |
    Then the user should be created successfully with:
      | code | type    | message |
      | 200  | unknown | 1001    |