package uk.gov.dft.bluebadge.webapp.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.Mappings;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.MobilityAidAddForm;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.MobilityAidListForm;

import javax.validation.Valid;
import java.util.ArrayList;

import static uk.gov.dft.bluebadge.webapp.citizen.model.Journey.FORM_REQUEST;
import static uk.gov.dft.bluebadge.webapp.citizen.model.Journey.JOURNEY_SESSION_KEY;

@Controller
@RequestMapping(Mappings.URL_MOBILITY_AID_LIST)
public class MobilityAidListController implements StepController {

  private static final String TEMPLATE = "mobility-aid-list";
  private final RouteMaster routeMaster;

  @Autowired
  public MobilityAidListController(RouteMaster routeMaster) {
    this.routeMaster = routeMaster;
  }

  @GetMapping
  public String show(@ModelAttribute(JOURNEY_SESSION_KEY) Journey journey, Model model) {
    if (!journey.isValidState(getStepDefinition())) {
      return routeMaster.backToCompletedPrevious();
    }

    // On returning to form, take previously submitted values.
    if (!model.containsAttribute(FORM_REQUEST) && null != journey.getMobilityAidListForm()) {
      model.addAttribute(FORM_REQUEST, journey.getMobilityAidListForm());
    }

    // If navigating forward from previous form, reset
    if (!model.containsAttribute(FORM_REQUEST)) {
      model.addAttribute(
          FORM_REQUEST, MobilityAidListForm.builder().mobilityAids(new ArrayList<>()).build());
    }
    return TEMPLATE;
  }

  @PostMapping
  public String submit(
      @ModelAttribute(JOURNEY_SESSION_KEY) Journey journey,
      @Valid @ModelAttribute(FORM_REQUEST) MobilityAidListForm mobilityAidListForm,
      BindingResult bindingResult,
      RedirectAttributes attr) {

    if (bindingResult.hasErrors()) {
      return routeMaster.redirectToOnBindingError(this, mobilityAidListForm, bindingResult, attr);
    }

    // Reset if no selected
    if (!mobilityAidListForm.getHasWalkingAid()) {
      mobilityAidListForm.setMobilityAids(new ArrayList<>());
      journey.setMobilityAidListForm(mobilityAidListForm);
    }
    // Don't overwrite mobility/AidList in journey else
    // as it is not bound to inputs in ui form

    return routeMaster.redirectToOnSuccess(mobilityAidListForm);
  }

  @RequestMapping(value = Mappings.URL_MOBILITY_AID_LIST + "/remove", method = RequestMethod.GET)
  public String handleDelete(
      @RequestParam(name = "uuid") String uuid,
      @ModelAttribute(JOURNEY_SESSION_KEY) Journey journey) {

    for (MobilityAidAddForm item : journey.getMobilityAidListForm().getMobilityAids()) {
      if (item.getId().equals(uuid)) {
        journey.getMobilityAidListForm().getMobilityAids().remove(item);
      }
    }
    return "redirect:" + Mappings.URL_MOBILITY_AID_LIST;
  }

  @Override
  public StepDefinition getStepDefinition() {
    return StepDefinition.MOBILITY_AID_LIST;
  }
}
