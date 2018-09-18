package uk.gov.dft.bluebadge.webapp.citizen.controllers;

import static uk.gov.dft.bluebadge.webapp.citizen.model.Journey.JOURNEY_SESSION_KEY;

import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.dft.bluebadge.webapp.citizen.client.referencedata.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.Mappings;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.RouteMaster;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.ApplicantForm;
import uk.gov.dft.bluebadge.webapp.citizen.model.form.ApplicantType;
import uk.gov.dft.bluebadge.webapp.citizen.model.view.ErrorViewModel;

@Controller
@RequestMapping(Mappings.URL_APPLICANT_TYPE)
public class ApplicantController implements StepController {
  private static final String TEMPLATE_APPLICANT = "applicant";
  private final RouteMaster routeMaster;

  public ApplicantController(RouteMaster routeMaster) {
    this.routeMaster = routeMaster;
  }

  @GetMapping
  public String show(
      Model model,
      @ModelAttribute("formRequest") ApplicantForm formRequest,
      @ModelAttribute(JOURNEY_SESSION_KEY) Journey journey) {

    if (null != journey.getApplicantForm()) {
      BeanUtils.copyProperties(journey.getApplicantForm(), formRequest);
    }
    List<ReferenceData> applicantOptions = getApplicantOptions();
    model.addAttribute("applicantOptions", applicantOptions);

    return TEMPLATE_APPLICANT;
  }

  private List<ReferenceData> getApplicantOptions() {
    ReferenceData yourself = new ReferenceData();
    yourself.setShortCode(ApplicantType.YOURSELF.toString());
    yourself.setDescription("Yourself");

    ReferenceData someone = new ReferenceData();
    someone.setShortCode(ApplicantType.SOMEONE_ELSE.toString());
    someone.setDescription("Someone else");

    return Lists.newArrayList(yourself, someone);
  }

  @PostMapping
  public String submit(
      @ModelAttribute(JOURNEY_SESSION_KEY) Journey journey,
      @Valid @ModelAttribute("formRequest") ApplicantForm formRequest,
      BindingResult bindingResult,
      Model model) {

    model.addAttribute("errorSummary", new ErrorViewModel());

    if (bindingResult.hasErrors()) {
      List<ReferenceData> applicantOptions = getApplicantOptions();
      model.addAttribute("applicantOptions", applicantOptions);
      return TEMPLATE_APPLICANT;
    }

    journey.setApplicantForm(formRequest);

    return routeMaster.redirectToOnSuccess(this);
  }

  @Override
  public StepDefinition getStepDefinition() {
    return StepDefinition.APPLICANT_TYPE;
  }
}
