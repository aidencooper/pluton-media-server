package net.aidencooper.pluton.mediaserver.media.ingestion;

import lombok.RequiredArgsConstructor;
import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.library.service.LibraryService;
import net.aidencooper.pluton.mediaserver.media.ingestion.classifier.MediaClassification;
import net.aidencooper.pluton.mediaserver.media.ingestion.classifier.port.IMediaClassifierService;
import net.aidencooper.pluton.mediaserver.media.ingestion.parser.adapter.MediaParserService;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMedia;
import net.aidencooper.pluton.mediaserver.media.ingestion.processor.adapter.MediaProcessorService;
import net.aidencooper.pluton.mediaserver.media.ingestion.scanner.adapter.MediaScannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class MediaIngestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaIngestionService.class);
    private final IMediaClassifierService mediaClassifierService;
    private final MediaParserService mediaParserService;
    private final MediaProcessorService mediaProcessorService;
    private final MediaScannerService mediaScannerService;
    private final LibraryService libraryService;

    public void ingestLibraries() {
        this.libraryService.getLibraries().forEach(this::ingestLibrary);
    }

    public void ingestLibrary(Library library) {
        this.mediaScannerService.scan(library).forEach(this::ingestFile);
    }

    private void ingestFile(Path path) {
        MediaClassification classification = this.mediaClassifierService.classify(path);
        if(classification == MediaClassification.IGNORE) return;

        try {
            ParsedMedia media = this.mediaParserService.parse(path, classification);
            this.mediaProcessorService.process(path, media);
        } catch (Exception exception) {
            LOGGER.error("Failed to process: {}", path, exception);
        }
    }
}
