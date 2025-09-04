package org.fistedar.black.list.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlackListResponse extends CoreResponse {

    private String personFirstName;
    private String personLastName;
    private String personCode;
    private Boolean blackListed;

    public BlackListResponse(List<ValidationError> errors) {
        super(errors);
    }
}
