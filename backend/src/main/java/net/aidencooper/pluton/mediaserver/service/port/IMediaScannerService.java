package net.aidencooper.pluton.mediaserver.service.port;

import net.aidencooper.pluton.mediaserver.domain.entity.Library;

public interface IMediaScannerService {
    void scanLibrary(Library library);
}
