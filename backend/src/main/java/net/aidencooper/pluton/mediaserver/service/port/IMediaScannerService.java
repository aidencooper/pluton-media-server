package net.aidencooper.pluton.mediaserver.service.port;

import net.aidencooper.pluton.mediaserver.domain.entity.Library;

public interface IMediaScannerService {
    void scan();
    void scan(Library library);
}
