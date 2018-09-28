@SubmitApplicationWPMSRoute
Feature: DFT Blue badge Citizen app new application - WPMS or not listed benifits
  As a citizen user I want to be able to create a new application via WPMS route or not listed benifits

  Scenario: Create a successful new application for myself - WPMS route
    Given I navigate to the "applicant" page
    Then  I should see the page titled "Who are you applying for? - GOV.UK Apply for a Blue Badge"
    And   I click on element "applicantType.label.YOURSELF"
    And   I can click on "Continue"

    Then  I should see the page titled "Choose your local council - GOV.UK Apply for a Blue Badge"
    When  I type "Worcester" for "councilShortCode" field by id
    And   I select "Worcester city council" from autosuggest council list
    And   I can click on "Continue"

    Then  I should see the page titled "Your issuing authority - GOV.UK Apply for a Blue Badge"
    And   I should see "Worcestershire county council" text on the page
    And   I can click on "Continue"

    Then  I should see the page titled "Do you receive any of these benefits? - GOV.UK Apply for a Blue Badge"
    And   I should see the title "Do you receive any of these benefits?"
    And   I select option "benefitType.option.WPMS"
    And   I can click on "Continue"

    Then  I should see the page titled "You're eligible for a Blue Badge - GOV.UK Apply for a Blue Badge"
    And   I should see the title "You're eligible for a Blue Badge"
    And   I can click on "Start application"

    Then  I should see the page titled "What's your name? - GOV.UK Apply for a Blue Badge"
    When  I type "Tom Richardson" for "fullName" field by id
    And   I select an option "hasBirthName.no"
    And   I can click on "Continue"

    Then  I should see the page titled "What's your date of birth - GOV.UK Apply for a Blue Badge"
    And   I should see the title "What's your date of birth?"
    When  I type day as "02" month as "08" and year as "1966" for applicant's date of birth
    And   I can click on "Continue"

    Then  I should see the page titled "What's your gender? - GOV.UK Apply for a Blue Badge"
    And   I should see the title "What's your gender?"
    When  I select an option "gender.MALE"
    And   I can click on "Continue"

    Then  I should see the page titled "Describe health conditions - GOV.UK Apply for a Blue Badge"
    And   I should see the title "Describe any health conditions that affect your mobility"
    When  I type "Sample health condition" for "descriptionOfConditions" field by id
    And   I can click on "Continue"

    Then  I should see the page titled "Declaration - GOV.UK Apply for a Blue Badge"
    And   I should see the content "I agree to this declaration"
    And   I select option "declaration.option"
    And   I can click on "Continue"

    Then  I should see the page titled "Application submitted - GOV.UK Apply for a Blue Badge"

  ##################################################################################################


  Scenario: Create a successful new application for someone else - WPMS route
    Given I navigate to the "applicant" page
    Then  I should see the page titled "Who are you applying for? - GOV.UK Apply for a Blue Badge"
    And   I click on element "applicantType.label.SOMEONE_ELSE"
    And   I can click on "Continue"

    Then  I should see the page titled "Choose their local council - GOV.UK Apply for a Blue Badge"
    When  I type "Worcester" for "councilShortCode" field by id
    And   I select "Worcester city council" from autosuggest council list
    And   I can click on "Continue"

    Then  I should see the page titled "Their issuing authority - GOV.UK Apply for a Blue Badge"
    And   I should see "Worcestershire county council" text on the page
    And   I can click on "Continue"

    Then  I should see the page titled "Do they receive any of these benefits? - GOV.UK Apply for a Blue Badge"
    And   I should see the title "Do they receive any of these benefits?"
    And   I select option "benefitType.option.WPMS"
    And   I can click on "Continue"

    Then  I should see the page titled "They are eligible for a Blue Badge - GOV.UK Apply for a Blue Badge"
    And   I should see the title "They are eligible for a Blue Badge"
    And   I can click on "Start application"

    Then  I should see the page titled "What's their name? - GOV.UK Apply for a Blue Badge"
    When  I type "Tom Richardson" for "fullName" field by id
    And   I select an option "hasBirthName.no"
    And   I can click on "Continue"

    Then  I should see the page titled "What's their date of birth - GOV.UK Apply for a Blue Badge"
    When  I type day as "02" month as "08" and year as "1966" for applicant's date of birth
    And   I can click on "Continue"

    Then  I should see the page titled "What's their gender? - GOV.UK Apply for a Blue Badge"
    And   I should see the title "What's their gender?"
    When  I select an option "gender.MALE"
    And   I can click on "Continue"

    Then  I should see the page titled "Describe health conditions - GOV.UK Apply for a Blue Badge"
    And   I should see the title "Describe any health conditions that affect their mobility"
    When  I type "Sample health condition" for "descriptionOfConditions" field by id
    And   I can click on "Continue"

    Then  I should see the page titled "Declaration - GOV.UK Apply for a Blue Badge"
    And   I should see the content "They agree to this declaration"
    And   I select option "declaration.option"
    And   I can click on "Continue"

    Then  I should see the page titled "Application submitted - GOV.UK Apply for a Blue Badge"

  ##################################################################################################


  Scenario: Create a successful new application for myself - not listed benefits
    Given I navigate to the "applicant" page
    Then  I should see the page titled "Who are you applying for? - GOV.UK Apply for a Blue Badge"
    And   I click on element "applicantType.label.YOURSELF"
    And   I can click on "Continue"

    Then  I should see the page titled "Choose your local council - GOV.UK Apply for a Blue Badge"
    When  I type "Worcester" for "councilShortCode" field by id
    And   I select "Worcester city council" from autosuggest council list
    And   I can click on "Continue"

    Then  I should see the page titled "Your issuing authority - GOV.UK Apply for a Blue Badge"
    And   I should see "Worcestershire county council" text on the page
    And   I can click on "Continue"

    Then  I should see the page titled "Do you receive any of these benefits? - GOV.UK Apply for a Blue Badge"
    And   I should see the title "Do you receive any of these benefits?"
    And   I select option "benefitType.option.WALKD"
    And   I can click on "Continue"

    Then  I should see the page titled "You may be eligible for a Blue Badge - GOV.UK Apply for a Blue Badge"
    And   I should see the title "You may be eligible for a Blue Badge"
    And   I can click on "Start application"

    Then  I should see the page titled "What's your name? - GOV.UK Apply for a Blue Badge"
    When  I type "Tom Richardson" for "fullName" field by id
    And   I select an option "hasBirthName.no"
    And   I can click on "Continue"

    Then  I should see the page titled "What's your date of birth - GOV.UK Apply for a Blue Badge"
    And   I should see the title "What's your date of birth?"
    When  I type day as "02" month as "08" and year as "1966" for applicant's date of birth
    And   I can click on "Continue"

    Then  I should see the page titled "What's your gender? - GOV.UK Apply for a Blue Badge"
    And   I should see the title "What's your gender?"
    When  I select an option "gender.FEMALE"
    And   I can click on "Continue"

    Then  I should see the page titled "Describe health conditions - GOV.UK Apply for a Blue Badge"
    And   I should see the title "Describe any health conditions that affect your mobility"
    When  I type "Sample health condition" for "descriptionOfConditions" field by id
    And   I can click on "Continue"

    Then  I should see the page titled "Declaration - GOV.UK Apply for a Blue Badge"
    And   I should see the content "I agree to this declaration"
    And   I select option "declaration.option"
    And   I can click on "Continue"

    Then  I should see the page titled "Application submitted - GOV.UK Apply for a Blue Badge"

    ##################################################################################################


  Scenario: Create a successful new application for someone else - Not listed benefits
    Given I navigate to the "applicant" page
    Then  I should see the page titled "Who are you applying for? - GOV.UK Apply for a Blue Badge"
    And   I click on element "applicantType.label.SOMEONE_ELSE"
    And   I can click on "Continue"

    Then  I should see the page titled "Choose their local council - GOV.UK Apply for a Blue Badge"
    When  I type "Worcester" for "councilShortCode" field by id
    And   I select "Worcester city council" from autosuggest council list
    And   I can click on "Continue"

    Then  I should see the page titled "Their issuing authority - GOV.UK Apply for a Blue Badge"
    And   I should see "Worcestershire county council" text on the page
    And   I can click on "Continue"

    Then  I should see the page titled "Do they receive any of these benefits? - GOV.UK Apply for a Blue Badge"
    And   I should see the title "Do they receive any of these benefits?"
    And   I select option "benefitType.option.WALKD"
    And   I can click on "Continue"

    Then  I should see the page titled "They may be eligible for a Blue Badge - GOV.UK Apply for a Blue Badge"
    And   I should see the title "They may be eligible for a Blue Badge"
    And   I can click on "Start application"

    Then  I should see the page titled "What's their name? - GOV.UK Apply for a Blue Badge"
    When  I type "Tom Richardson" for "fullName" field by id
    And   I select an option "hasBirthName.no"
    And   I can click on "Continue"

    Then  I should see the page titled "What's their date of birth - GOV.UK Apply for a Blue Badge"
    When  I type day as "02" month as "08" and year as "1966" for applicant's date of birth
    And   I can click on "Continue"

    Then  I should see the page titled "What's their gender? - GOV.UK Apply for a Blue Badge"
    And   I should see the title "What's their gender?"
    When  I select an option "gender.UNSPECIFIE"
    And   I can click on "Continue"

    Then  I should see the page titled "Describe health conditions - GOV.UK Apply for a Blue Badge"
    And   I should see the title "Describe any health conditions that affect their mobility"
    When  I type "Sample health condition" for "descriptionOfConditions" field by id
    And   I can click on "Continue"

    Then  I should see the page titled "Declaration - GOV.UK Apply for a Blue Badge"
    And   I should see the content "They agree to this declaration"
    And   I select option "declaration.option"
    And   I can click on "Continue"

    Then  I should see the page titled "Application submitted - GOV.UK Apply for a Blue Badge"
