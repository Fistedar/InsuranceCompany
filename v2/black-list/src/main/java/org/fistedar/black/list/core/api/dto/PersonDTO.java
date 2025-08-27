package org.fistedar.black.list.core.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDTO {

    private String personFirstName;
    private String personLastName;
    private String personCode;
    private Boolean blackListed;
}
