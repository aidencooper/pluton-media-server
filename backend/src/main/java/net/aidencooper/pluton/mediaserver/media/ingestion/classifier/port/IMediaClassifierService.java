package net.aidencooper.pluton.mediaserver.media.ingestion.classifier.port;

import net.aidencooper.pluton.mediaserver.media.ingestion.classifier.MediaClassification;

import java.nio.file.Path;

public interface IMediaClassifierService {
    MediaClassification classify(Path path);
}
