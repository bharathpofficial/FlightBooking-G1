@adamya
Feature: Flight Search Functionality

  Background:
    Given I am logged in with valid credentials
    And I navigate to the flight search page

  Scenario: Search flight by Flight Number
    Then I enter a valid flight number in the "Search by Flight Number" field
    Then I click the "Search by Flight Number" button
    And I should see the flight details corresponding to that flight number

  Scenario: Search flight by Flight Name
    Then I enter a valid flight name in the "Search by Flight Name" field
    Then I click the "Search by Flight Name" button
    And I should see the flight details corresponding to that flight name

  Scenario: Search flight by Flight Type
    Then I enter a valid flight type in the "Search by Flight Type" field
    Then I click the "Search by Flight Type" button
    And I should see the flight details of all flights matching that type