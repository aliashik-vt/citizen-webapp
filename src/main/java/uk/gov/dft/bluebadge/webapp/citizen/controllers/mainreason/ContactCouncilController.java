package uk.gov.dft.bluebadge.webapp.citizen.controllers.mainreason;

import static uk.gov.dft.bluebadge.webapp.citizen.model.Journey.JOURNEY_SESSION_KEY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.StepController;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.Mappings;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;

@Controller
@RequestMapping(Mappings.URL_CONTACT_COUNCIL)
public class ContactCouncilController implements StepController {
  private static final String TEMPLATE = "mainreason/contact-council";

  private final RouteMaster routeMaster;

  @Autowired
  public ContactCouncilController(RouteMaster routeMaster) {
    this.routeMaster = routeMaster;
  }

  @GetMapping
  public String show(
      @ModelAttribute(JOURNEY_SESSION_KEY) Journey journey,
      Model model,
      SessionStatus sessionStatus) {
    if (!journey.isValidState(getStepDefinition())) {
      return routeMaster.backToCompletedPrevious();
    }

    String laShortCode = journey.getYourIssuingAuthorityForm().getLocalAuthorityShortCode();
    model.addAttribute("localAuthority", journey.getLocalAuthority());

    sessionStatus.setComplete();
    return TEMPLATE;
  }

  @Override
  public StepDefinition getStepDefinition() {
    return StepDefinition.CONTACT_COUNCIL;
  }
}
