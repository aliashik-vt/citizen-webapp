package uk.gov.service.bluebadge.test.acceptance.steps;

class Ids {
  class Preamble {
    static final String APPLICANT_TYPE_OPTION_LIST = "applicantType";
    static final String APPLICANT_TYPE_YOURSELF_OPTION = APPLICANT_TYPE_OPTION_LIST + ".YOURSELF";
    static final String APPLICANT_TYPE_SOMELSE_OPTION =
        APPLICANT_TYPE_OPTION_LIST + ".SOMEONE_ELSE";
    static final String COUNCIL_INPUT = "councilShortCode";
  }

  class EleCheck {
    static final String BENEFIT_RECEIVED_LIST = "benefitType";
    static final String MAIN_REASON_LIST = "mainReasonOption";
    static final String WALKING_DIFFICULTY_LIST = "walkingDifficulty";
    static final String HAS_RECEIVED_DLA = "receivedDlaOption.HAS_RECEIVED_DLA";
    static final String NEVER_RECEIVED_DLA = "receivedDlaOption.NEVER_RECEIVED_DLA";
    static final String MOVING_POINTS = "movingAroundPoints.MOVING_POINTS";
    static final String PLANNING_POINTS = "planningJourneyOption.PLANNING_POINTS";
    static final String GENDER_MALE = "gender.MALE";
    static final String GENDER_FEMALE = "gender.FEMALE";
    static final String GENDER_UNSPECIFIED = "gender.UNSPECIFIE";
    static final String NI = "nino";
    static final String NO_NI_TEXT = "You don't have a National Insurance number";
    static final String NO_NI_LINK_TEXT = "Continue without a National Insurance number.";
    static final String FEEDBACK_URL = "https://www.smartsurvey.co.uk/s/XIYJ6/";
    static final String GOOGLE_ANALYTICS_TAG = "UA-124760983-5";
  }

  class Person {
    static final String NAME = "fullName";
    static final String HAS_BIRTH_NAME_NO_OPTION = "hasBirthName.no";
    static final String DOB_DAY = "dateOfBirth.day";
    static final String DOB_MONTH = "dateOfBirth.month";
    static final String DOB_YEAR = "dateOfBirth.year";
  }

  class Contact {
    static final String FULL_NAME = "fullName";
    static final String PRIMARY_CONTACT_NUMBER = "primaryPhoneNumber";
    static final String SECONDARY_CONTACT_NUMBER = "secondaryPhoneNumber";
    static final String EMAIL_ADDRESS = "emailAddress";
  }
}
