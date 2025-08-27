package org.fistedar.black.list.core.service;

import lombok.RequiredArgsConstructor;
import org.fistedar.black.list.core.api.command.BlacklistCoreCommand;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {


    @Override
    public BlacklistCoreCommand checkPersonFromBlacklist(BlacklistCoreCommand command) {
        return null;
    }
}
