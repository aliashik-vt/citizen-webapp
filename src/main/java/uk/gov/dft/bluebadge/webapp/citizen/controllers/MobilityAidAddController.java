package uk.gov.dft.bluebadge.webapp.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.citizen.client.applicationmanagement.model.HowProvidedCodeField;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.Mappings;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;
import uk.gov.dft.bluebadge.webapp.citizen.model.RadioOptionsGroup;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.MobilityAidAddForm;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.MobilityAidListForm;

import javax.validation.Valid;
import java.util.ArrayList;

import static uk.gov.dft.bluebadge.webapp.citizen.model.Journey.FORM_REQUEST;
import static uk.gov.dft.bluebadge.webapp.citizen.model.Journey.JOURNEY_SESSION_KEY;

@Controller
@RequestMapping(Mappings.URL_MOBILITY_AID_ADD)
public class MobilityAidAddController implements StepController {

  private RouteMaster routeMaster;
  private static final String TEMPLATE = "mobility-aid-add";

  @Autowired
  public MobilityAidAddController(RouteMaster routeMaster) {
    this.routeMaster = routeMaster;
  }

  @GetMapping
  public String show(@ModelAttribute(JOURNEY_SESSION_KEY) Journey journey, Model model) {

    if (!journey.isValidState(getStepDefinition())) {
      return routeMaster.backToCompletedPrevious();
    }

    // Can hit add link before previous form submitted.
    if (null == journey.getMobilityAidListForm()
        || null == journey.getMobilityAidListForm().getMobilityAids()) {
      journey.setMobilityAidListForm(
          MobilityAidListForm.builder()
              .mobilityAids(new ArrayList<>())
              .build());
    }

    journey.getMobilityAidListForm().setHasWalkingAid(true);

    // On returning to form, take previously submitted values.
    if (!model.containsAttribute(FORM_REQUEST)) {
      model.addAttribute(FORM_REQUEST, new MobilityAidAddForm());
    }

    model.addAttribute(
        "aidTypeOptions",
        new RadioOptionsGroup.Builder()
            .titleMessageKey("mobilityaid.type.optiongroup.title")
            .titleIsLabel()
            .addOption(MobilityAidAddForm.AidType.WHEELCHAIR, "mobilityaid.type.option.wheelchair")
            .addOption(MobilityAidAddForm.AidType.SCOOTER, "mobilityaid.type.option.scooter")
            .addOption(MobilityAidAddForm.AidType.WALKING_AID, "mobilityaid.type.option.walkingaid")
            .build());
    model.addAttribute(
        "howProvidedOptions",
        new RadioOptionsGroup.Builder()
            .titleIsLabel()
            .titleMessageKeyApplicantAware("mobilityaid.howprovided.optiongroup.title", journey)
            .addOption(HowProvidedCodeField.PRESCRIBE, "mobilityaid.howprovided.option.prescribed")
            .addOption(HowProvidedCodeField.PRIVATE, "mobilityaid.howprovided.option.private")
            .addOption(HowProvidedCodeField.SOCIAL, "mobilityaid.howprovided.option.social")
            .build());
    return TEMPLATE;
  }

  @PostMapping
  public String submit(
      @ModelAttribute(JOURNEY_SESSION_KEY) Journey journey,
      @Valid @ModelAttribute(FORM_REQUEST) MobilityAidAddForm mobilityAidAddForm,
      BindingResult bindingResult,
      RedirectAttributes attr) {

    if (bindingResult.hasErrors()) {
      return routeMaster.redirectToOnBindingError(this, mobilityAidAddForm, bindingResult, attr);
    }

    journey.getMobilityAidListForm().getMobilityAids().add(mobilityAidAddForm);

    return "redirect:" + Mappings.URL_MOBILITY_AID_LIST;
  }

  @Override
  public StepDefinition getStepDefinition() {
    return StepDefinition.MOBILITY_AID_ADD;
  }
}
