package uk.gov.dft.bluebadge.webapp.citizen.model.form;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import uk.gov.dft.bluebadge.webapp.citizen.client.applicationmanagement.model.EligibilityCodeField;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepDefinition;
import uk.gov.dft.bluebadge.webapp.citizen.controllers.journey.StepForm;
import uk.gov.dft.bluebadge.webapp.citizen.model.Journey;

@Data
@Builder
public class HealthcareProfessionalListForm implements Serializable, StepForm {

  List<HealthcareProfessionalAddForm> healthcareProfessionals;

  @NotNull private String hasHealthcareProfessional;

  @Override
  public StepDefinition getAssociatedStep() {
    return StepDefinition.HEALTHCARE_PROFESSIONAL_LIST;
  }
}
