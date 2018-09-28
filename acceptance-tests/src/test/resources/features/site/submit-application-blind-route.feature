@SubmitApplicationBLINDRoute
Feature: DFT Blue badge Citizen app new application - TERMILL
  As a citizen user I want to be able to get information on council details via TERMILL route

  Scenario: Blind application for yourself
    Given I complete applicant page for "yourself"
    And   I complete select council page
    And   I complete your local authority page
    And   I complete receive benefit page for "NONE"
    Then  I should see the page titled "What is the main reason you need a badge?" with GOV.UK suffix
    And   I should see the title "What is the main reason you need a badge?"
    And   I can click on "Continue"
    And   I should see error summary box
    And   I select option "mainReasonOption.option.BLIND"
    And   I can click on "Continue"

    Then  I should see the page titled "You're eligible for a Blue Badge" with GOV.UK suffix
    And   I should see the title "You're eligible for a Blue Badge"
    And   I can click on "Start application"

    Then  I should see the page titled "What's your name?" with GOV.UK suffix
    When  I type "Tom Richardson" for "fullName" field by id
    And   I select an option "hasBirthName.no"
    And   I can click on "Continue"

    Then  I should see the page titled "What's your date of birth" with GOV.UK suffix
    When  I type day as "02" month as "08" and year as "1966" for applicant's date of birth
    And   I can click on "Continue"

    Then  I should see the page titled "Describe health conditions" with GOV.UK suffix
    And   I should see the title "Describe any health conditions that affect your mobility"
    When  I type "Sample health condition" for "descriptionOfConditions" field by id
    And   I can click on "Continue"

    Then  I should see the page titled "Declaration" with GOV.UK suffix
    And   I should see the content "I agree to this declaration"
    And   I select option "declaration.option"
    And   I can click on "Continue"

    Then  I should see the page titled "Application submitted" with GOV.UK suffix


  Scenario: Blind application for someone else
    Given I complete applicant page for "someone else"
    And   I complete select council page
    And   I complete your local authority page
    And   I complete receive benefit page for "NONE"
    Then  I should see the page titled "What is the main reason they need a badge?" with GOV.UK suffix
    And   I should see the title "What is the main reason they need a badge?"
    And   I select option "mainReasonOption.option.BLIND"
    And   I can click on "Continue"

    Then  I should see the page titled "They are eligible for a Blue Badge" with GOV.UK suffix
    And   I should see the title "They are eligible for a Blue Badge"
    And   I can click on "Start application"

    Then  I should see the page titled "What's their name?" with GOV.UK suffix
    When  I type "Tom Richardson" for "fullName" field by id
    And   I can click on "Continue"
    And   I should see error summary box
    And   I select an option "hasBirthName.no"
    And   I can click on "Continue"

    Then  I should see the page titled "What's their date of birth" with GOV.UK suffix
    And   I can click on "Continue"
    And   I should see error summary box
    When  I type day as "02" month as "08" and year as "1966" for applicant's date of birth
    And   I can click on "Continue"

    Then  I should see the page titled "Describe health conditions" with GOV.UK suffix
    And   I should see the title "Describe any health conditions that affect their mobility"
    And   I can click on "Continue"
    And   I should see error summary box
    When  I type "Sample health condition" for "descriptionOfConditions" field by id
    And   I can click on "Continue"

    Then  I should see the page titled "Declaration" with GOV.UK suffix
    And   I should see the content "They agree to this declaration"
    And   I can click on "Continue"
    And   I should see error summary box
    And   I select option "declaration.option"
    And   I can click on "Continue"

    Then  I should see the page titled "Application submitted" with GOV.UK suffix

