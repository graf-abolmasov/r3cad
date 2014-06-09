Feature: Cucumber and Geb
  As a developer I can use Cucumber
  and Geb for functional testing

  Scenario: Use Geb in Cucumber's steps
    Given I visit root page
    Then I should see page with title "Open Project - R3"

  Scenario: Use RemoteControl in Cucumber's steps
    Given I have 2 demo project in the database
    When I visit root page
    Then I should see "Демонстрационный проект 1"

  Scenario: Click buttons
    Given I visit root page
    When I click create new demo project button
    Then I should have 1 project in the database
    Then I should see "Демонстрационный проект 0"

  Scenario: Reset database
    Given I have 2 demo project in the database
    When I clean the database
    Then I should have 0 project in the database

  Scenario: Clean up DB after each scenario
    Given I should have 0 project in the database
