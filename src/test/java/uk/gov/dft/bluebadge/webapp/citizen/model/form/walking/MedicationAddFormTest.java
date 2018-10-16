package uk.gov.dft.bluebadge.webapp.citizen.model.form.walking;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MedicationAddFormTest {

  @Test
  public void getPrescribedValue() {
    MedicationAddForm form = new MedicationAddForm();
    form.setPrescribed(null);
    assertNull(form.getPrescribedValue());

    form.setPrescribed("no");
    assertFalse(form.getPrescribedValue());

    form.setPrescribed("yes");
    assertTrue(form.getPrescribedValue());

    form.setPrescribed("bob");
    assertNull(form.getPrescribedValue());
  }
}
