package uk.gov.dft.bluebadge.webapp.citizen.controllers;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.citizen.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.citizen.client.applicationmanagement.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.fixture.JourneyFixture;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.ApplicantForm;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.ApplicantType;

public abstract class ControllerTestFixture<T> {

  protected MockMvc mockMvc;
  protected Journey journey;

  protected abstract String getTemplateName();

  protected abstract String getUrl();

  protected abstract StepDefinition getStep();

  protected abstract EligibilityCodeField getEligibilityType();

  protected void setup(T controller) {
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
    journey = JourneyFixture.getDefaultJourneyToStep(getStep(), getEligibilityType());
    ApplicantForm applicantForm =
        ApplicantForm.builder().applicantType(ApplicantType.YOURSELF.toString()).build();
    journey.setApplicantForm(applicantForm);
  }

  protected static ResultMatcher formRequestFlashAttributeHasFieldErrorCode(
      String fieldName, String error) {
    return flash()
        .attribute(
            "org.springframework.validation.BindingResult.formRequest",
            hasProperty(
                "fieldErrors",
                hasItem(
                    allOf(
                        hasProperty("field", equalTo(fieldName)),
                        hasProperty("code", equalTo(error))))));
  }

  protected void applyRoutmasterDefaultMocks(RouteMaster mockRouteMaster) {
    when(mockRouteMaster.backToCompletedPrevious()).thenReturn("backToStart");
    when(mockRouteMaster.redirectToOnBindingError(any(), any(), any(), any())).thenCallRealMethod();
  }

  protected void show_ShouldDisplayTemplate() throws Exception {
    mockMvc
        .perform(get(getUrl()).sessionAttr("JOURNEY", journey))
        .andExpect(status().isOk())
        .andExpect(view().name(getTemplateName()))
        .andExpect(model().attributeExists(Journey.FORM_REQUEST));
  }

  protected void show_shouldRedirect_whenJourneyNotSetup() throws Exception {
    mockMvc
        .perform(get(getUrl()).sessionAttr("JOURNEY", new Journey()))
        .andExpect(status().isOk())
        .andExpect(view().name("backToStart"));
  }
}
