package uk.gov.dft.bluebadge.webapp.citizen.client.referencedata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LocalCouncilRefData extends ReferenceData {
  @JsonProperty("metaData")
  private LocalCouncilMetaData localCouncilMetaData;

  @JsonIgnore
  public String getIssuingAuthorityShortCode() {
    return getLocalCouncilMetaData()
        .map(LocalCouncilMetaData::getIssuingAuthorityShortCode)
        .orElse(null);
  }

  public Optional<LocalCouncilMetaData> getLocalCouncilMetaData() {
    return Optional.ofNullable(localCouncilMetaData);
  }

  @Data
  public static class LocalCouncilMetaData implements Serializable {
    private String issuingAuthorityShortCode;
  }
}
