package uk.gov.dft.bluebadge.webapp.citizen.controllers;

import static uk.gov.dft.bluebadge.webapp.citizen.model.Journey.JOURNEY_SESSION_KEY;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.Mappings;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.ApplicantNameForm;

@Controller
@RequestMapping(Mappings.URL_APPLICANT_NAME)
public class ApplicantNameController implements StepController {

  private static final String TEMPLATE_APPLICANT_NAME = "applicant-name";
  public static final String FORM_REQUEST = "formRequest";

  private final RouteMaster routeMaster;

  @Autowired
  public ApplicantNameController(RouteMaster routeMaster) {
    this.routeMaster = routeMaster;
  }

  @GetMapping
  public String show(Model model, @ModelAttribute(JOURNEY_SESSION_KEY) Journey journey) {

    if (!journey.isValidState(getStepDefinition())) {
      return routeMaster.backToCompletedPrevious();
    }

    //On returning to form, take previously submitted values.
    if (!model.containsAttribute(FORM_REQUEST) && journey.hasStepForm(getStepDefinition())) {
      model.addAttribute(FORM_REQUEST, journey.getFormForStep(getStepDefinition()));
    }

    // If navigating forward from previous form, reset
    if (!model.containsAttribute(FORM_REQUEST)) {
      model.addAttribute(FORM_REQUEST, ApplicantNameForm.builder().build());
    }

    return TEMPLATE_APPLICANT_NAME;
  }

  @PostMapping
  public String submit(
      @ModelAttribute(JOURNEY_SESSION_KEY) Journey journey,
      @Valid @ModelAttribute(FORM_REQUEST) ApplicantNameForm applicantNameForm,
      BindingResult bindingResult,
      RedirectAttributes attr) {

    if (!applicantNameForm.isBirthNameValid()) {
      bindingResult.rejectValue("birthName", "field.birthName.NotBlank");
    }

    if (bindingResult.hasErrors()) {
      return routeMaster.redirectToOnBindingError(this, applicantNameForm, bindingResult, attr);
    }

    if (!applicantNameForm.getHasBirthName()) {
      applicantNameForm.setBirthName(applicantNameForm.getFullName());
    }

    journey.setFormForStep(applicantNameForm);
    return routeMaster.redirectToOnSuccess(applicantNameForm);
  }

  @Override
  public StepDefinition getStepDefinition() {
    return StepDefinition.NAME;
  }
}
