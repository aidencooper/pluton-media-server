package net.aidencooper.pluton.mediaserver.media.ingestion.parser.port;

import net.aidencooper.pluton.mediaserver.media.ingestion.classifier.MediaClassification;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMedia;

import java.nio.file.Path;

public interface IMediaParserService {
    ParsedMedia parse(Path path, MediaClassification classification);
}
