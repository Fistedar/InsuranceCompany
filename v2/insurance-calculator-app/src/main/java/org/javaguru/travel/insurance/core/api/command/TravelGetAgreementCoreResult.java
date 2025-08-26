package org.javaguru.travel.insurance.core.api.command;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelGetAgreementCoreResult {

    private AgreementDTO agreement;
    private List<ValidationErrorDTO> errors;

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public TravelGetAgreementCoreResult(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }
}
