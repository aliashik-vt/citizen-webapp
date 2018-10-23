package uk.gov.dft.bluebadge.webapp.citizen.controllers.pip;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.citizen.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.citizen.client.applicationmanagement.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.fixture.JourneyFixture;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.pip.PipMovingAroundForm;

public class PipMovingAroundControllerTest {
  private MockMvc mockMvc;

  @Mock private RouteMaster mockRouteMaster;
  private Journey journey;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    PipMovingAroundController controller = new PipMovingAroundController(mockRouteMaster);
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
    journey =
        JourneyFixture.getDefaultJourneyToStep(
            StepDefinition.PIP_PLANNING_JOURNEY, EligibilityCodeField.PIP);

    when(mockRouteMaster.backToCompletedPrevious()).thenReturn("backToStart");
    when(mockRouteMaster.redirectToOnBindingError(any(), any(), any(), any()))
        .thenReturn("redirect:/someValidationError");
  }

  @Test
  public void show_ShouldDisplayMovingroundTemplate() throws Exception {

    mockMvc
        .perform(get("/moving-around").sessionAttr("JOURNEY", journey))
        .andExpect(status().isOk())
        .andExpect(view().name("pip/moving-around"))
        .andExpect(model().attribute("formRequest", JourneyFixture.getPipMovingAroundForm()))
        .andExpect(model().attribute("formOptions", Matchers.notNullValue()));
  }

  @Test
  public void show_givenNoSession_ShouldRedirectBackToStart() throws Exception {

    when(mockRouteMaster.backToCompletedPrevious()).thenReturn("redirect:/backToStart");

    mockMvc
        .perform(get("/moving-around"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/backToStart"));
  }

  @Test
  public void submit_givenValidForm_thenShouldDisplayRedirectToSuccess() throws Exception {

    when(mockRouteMaster.redirectToOnSuccess(any(PipMovingAroundForm.class), any(Journey.class)))
        .thenReturn("redirect:/testSuccess");

    mockMvc
        .perform(
            post("/moving-around")
                .param("movingAroundPoints", "MOVING_POINTS_8")
                .sessionAttr("JOURNEY", journey))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/testSuccess"));
  }

  @Test
  public void submit_whenMissingMovingroundAnswer_ThenShouldHaveValidationError() throws Exception {
    mockMvc
        .perform(post("/moving-around").sessionAttr("JOURNEY", journey))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/someValidationError"));
  }
}
