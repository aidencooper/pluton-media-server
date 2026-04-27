package net.aidencooper.pluton.mediaserver.media.ingestion.classifier.adapter;

import net.aidencooper.pluton.mediaserver.media.ingestion.classifier.MediaClassification;
import net.aidencooper.pluton.mediaserver.media.ingestion.classifier.port.IMediaClassifierService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class MediaClassifierService implements IMediaClassifierService {
    @Override
    public MediaClassification classify(Path path) {
        String name = path.getFileName().toString().toLowerCase();

        if(this.isJunk(name)) return MediaClassification.IGNORE;
        if(this.isEpisode(name)) return MediaClassification.EPISODE;
        if(this.isMovie(name)) return MediaClassification.MOVIE;

        return MediaClassification.UNKNOWN;
    }

    private boolean isJunk(String name) {
        return name.contains("sample") || name.contains("trailer") || name.contains("extras");
    }

    private boolean isEpisode(String name) {
        return name.matches(".*s\\d{1,2}e\\d{1,2}.*");
    }

    private boolean isMovie(String name) {
        return name.matches(".*\\(\\d{4}\\).*") || name.matches(".*\\d{4}.*");
    }
}
