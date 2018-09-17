package uk.gov.dft.bluebadge.webapp.citizen.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.citizen.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.DeclarationForm;
import uk.gov.dft.bluebadge.webapp.citizen.service.ApplicationManagementService;

public class DeclarationSubmitControllerTest {

  private MockMvc mockMvc;
  private DeclarationSubmitController controller;

  @Mock ApplicationManagementService appService;
  @Mock private RouteMaster mockRouteMaster;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller = new DeclarationSubmitController(appService, mockRouteMaster);
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();
  }

  @Test
  public void showDeclaration_ShouldDisplayDeclarationTemplate() throws Exception {

    DeclarationForm formRequest = DeclarationForm.builder().build();

    mockMvc
        .perform(get("/apply-for-a-blue-badge/declaration"))
        .andExpect(status().isOk())
        .andExpect(view().name("application-end/declaration"))
        .andExpect(model().attribute("formRequest", formRequest));
  }

  @Test
  public void submitDeclaration_ShouldDisplayApplicationSubmittedTemplate_WhenDeclarationIsAgreed()
      throws Exception {

    when(mockRouteMaster.redirectToOnSuccess(controller)).thenReturn("redirect:/testSuccess");

    mockMvc
        .perform(post("/apply-for-a-blue-badge/declaration").param("agreed", "true"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/testSuccess"));
  }

  @Test
  public void submitDeclaration_ShouldThrowValidationError_WhenDeclarationIsNotAgreed()
      throws Exception {
    mockMvc
        .perform(post("/apply-for-a-blue-badge/declaration").param("agreed", "false"))
        .andExpect(status().isOk())
        .andExpect(view().name("application-end/declaration"))
        .andExpect(model().attributeHasFieldErrorCode("formRequest", "agreed", "AssertTrue"));
  }
}
