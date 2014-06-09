Feature: Common data set's
  As a user I can manage my data sets

  Scenario: Open edit page
    Given I have 1 demo project in the database
    When I open data set "Какие-то данные проекта" for edit
    Then I should see "Update"
    And I should see "Close Window"
