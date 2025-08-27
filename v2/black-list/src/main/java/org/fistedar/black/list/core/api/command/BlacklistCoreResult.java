package org.fistedar.black.list.core.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistCoreResult {
    private List<ValidationErrorDTO> errors;
    private PersonDTO person;

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
}
