package org.fistedar.black.list.core.service;

import org.fistedar.black.list.core.api.command.BlacklistCoreCommand;
import org.fistedar.black.list.core.api.command.BlacklistCoreResult;

public interface BlacklistService {
    BlacklistCoreResult checkPersonFromBlacklist(BlacklistCoreCommand command);
}
