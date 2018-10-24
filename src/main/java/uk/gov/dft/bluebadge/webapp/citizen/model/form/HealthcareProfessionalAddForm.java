package uk.gov.dft.bluebadge.webapp.citizen.model.form;

import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class HealthcareProfessionalAddForm implements Serializable {

  @Size(max = 100)
  @NotBlank
  private String healthcareProfessionalName;

  @Size(max = 100)
  @NotBlank
  private String healthcareProfessionalLocation;

  private String id = UUID.randomUUID().toString();
}
