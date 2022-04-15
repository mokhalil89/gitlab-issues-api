Feature: Gitlab Issues API

  @Regression
  Scenario:Valid Case: Get list of projects in gitlab
    Given User provide token in the header
    When  User call the list projects Api
    Then  The response code should be 200


  Scenario Outline: Valid Case: Verify creating project in gitlab successfully
    Given User provide token in the header
    Given Set project name "<name>" with random time in the payload
    When  User call the create project Api
    Then  The response code should be 201
    And   The project name should start with "<name>"
    Examples:
      | name                |
      | ABN-AMRO-Testing-QA |

  Scenario Outline: Valid Case: Create a new issue successfully
    Given User provide token in the header
    Given   Set issue title "<issue_title>" with random time in the payload
    When  User call the Create issue Api
    Then  The response code should be 201
    And   The issue title should start with "<issue_title>"
    Examples:
      | issue_title    |
      | ABN-AMRO-issue |

  Scenario Outline: Valid Case: Get list of issues for project
    Given User provide token in the header
    When  User call the list issues of project Api
    Then  The response code should be 200
    And   The issue type should be "<type>"
    Examples:
      | type  |
      | ISSUE |


  Scenario Outline: Valid Case: Edit an existing issue successfully
    Given User provide token in the header
    Given Set the issue title "<issue_title>" in the payload
    When  User call edit issue Api
    Then  The response code should be 200
    And   The issue title should start with "<issue_title>"
    Examples:
      | issue_title    |
      | ABN-AMRO-issue |


  Scenario Outline: Valid Case: Clone an issue successfully
    Given User provide token in the header
    Given Set to_project_id "<to_project_id>" in the payload
    When  User call clone issue Api
    Then  The response code should be 201
    And   The updatedAt is valid
    Examples:
      | to_project_id |
      | 35325299      |


  Scenario Outline: Valid Case: Move an issue from project to another project successfully
    Given User provide token in the header
    Given Set to_project_id "<to_project_id>" in the payload
    When  User call move issue project Api
    Then  The response code should be 201
    And   The to project id should be "<to_project_id>"
    Examples:
      | to_project_id |
      | 35325299      |

  Scenario Outline: Valid Case: Set a time estimate for an issue successfully
    Given User provide token in the header
    Given Set the duration "<duration>" in the payload
    When  User call set issue estimate time Api
    Then  The response code should be 200
    And   Time estimate should be "<duration>h"
    Examples:
      | duration |
      | 20       |

  Scenario: Valid Case: Delete an issue
    Given User provide token in the header
    When  User call delete issue Api
    Then  The response code should be 204


