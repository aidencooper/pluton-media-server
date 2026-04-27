package net.aidencooper.pluton.mediaserver.media.ingestion.processor.port;

import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMedia;

import java.nio.file.Path;

public interface IMediaProcessorService {
    void process(Path file, ParsedMedia media);
}
