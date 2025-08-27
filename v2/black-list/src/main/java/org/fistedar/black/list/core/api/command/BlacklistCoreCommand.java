package org.fistedar.black.list.core.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fistedar.black.list.core.api.dto.PersonDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistCoreCommand {
    private PersonDTO person;
}
