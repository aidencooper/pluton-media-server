package net.aidencooper.pluton.mediaserver.media.ingestion.scanner.port;

import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IMediaScannerService {
    Stream<Path> scan(Library library);
}
