package org.fistedar.black.list.core.service;

import org.fistedar.black.list.core.api.command.BlacklistCoreCommand;

public interface BlacklistService {
    BlacklistCoreCommand checkPersonFromBlacklist(BlacklistCoreCommand command);
}
