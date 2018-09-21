package uk.gov.dft.bluebadge.webapp.citizen.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.ApplicantForm;
import uk.gov.dft.bluebadge.webapp.citizen.service.referencedata.ReferenceDataService;

public class EligibleControllerTest {

  private MockMvc mockMvc;
  private EligibleController controller;

  @Mock private RouteMaster mockRouteMaster;
  @Mock private ReferenceDataService referenceDataService;
  private Journey journey;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller = new EligibleController(mockRouteMaster, referenceDataService);
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
    journey = new Journey();
    journey.setApplicantForm(ApplicantForm.builder().build());
  }

  @Test
  public void show_ShouldDisplayEligibleTemplate() throws Exception {

    mockMvc
        .perform(get("/eligible").sessionAttr("JOURNEY", journey))
        .andExpect(status().isOk())
        .andExpect(view().name("eligible"))
        .andExpect(model().attribute("formRequest", Matchers.nullValue()));
  }

  @Test
  public void startApplication_ShouldRedirectToNextPage() throws Exception {

    when(mockRouteMaster.redirectToOnSuccess(StepDefinition.ELIGIBLE))
        .thenReturn("redirect:/theNextPage");

    mockMvc
        .perform(get("/eligible/start"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/theNextPage"));
  }
}
