package org.fistedar.black.list.core.validation;

import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;

import java.util.List;

public interface BlackListValidation {
    List<ValidationErrorDTO> validate(PersonDTO person);
}
