package uk.gov.dft.bluebadge.webapp.citizen.controllers.organisation;

import static uk.gov.dft.bluebadge.webapp.citizen.model.Journey.JOURNEY_SESSION_KEY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.StepController;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.Mappings;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;

@Controller
@RequestMapping(Mappings.URL_ORGANISATION_MAY_BE_ELIGIBLE)
public class OrganisationMayBeEligibleController implements StepController {

  private static final String TEMPLATE = "organisation-may-be-eligible";

  private final RouteMaster routeMaster;

  @Autowired
  public OrganisationMayBeEligibleController(RouteMaster routeMaster) {
    this.routeMaster = routeMaster;
  }

  @GetMapping
  public String show(@ModelAttribute(JOURNEY_SESSION_KEY) Journey journey, Model model) {

    if (!journey.isValidState(getStepDefinition())) {
      return routeMaster.backToCompletedPrevious();
    }

    model.addAttribute("localAuthority", journey.getLocalAuthority());

    return TEMPLATE;
  }

  @GetMapping("/start")
  public String startApplication(@ModelAttribute(JOURNEY_SESSION_KEY) Journey journey) {
    return routeMaster.redirectToOnSuccess(StepDefinition.ORGANISATION_MAY_BE_ELIGIBLE);
  }

  @Override
  public StepDefinition getStepDefinition() {
    return StepDefinition.ORGANISATION_MAY_BE_ELIGIBLE;
  }
}
