package net.aidencooper.pluton.mediaserver.media.ingestion.processor.adapter;

import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedEpisode;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMedia;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMovie;
import net.aidencooper.pluton.mediaserver.media.ingestion.processor.port.IMediaProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class MediaProcessorService implements IMediaProcessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaProcessorService.class);

    @Override
    public void process(Path file, ParsedMedia media) {
        switch (media) {
            case ParsedMovie movie -> this.processMovie(file, movie);
            case ParsedEpisode episode -> this.processEpisode(file, episode);
        }
    }

    private void processMovie(Path file, ParsedMovie movie) {
        LOGGER.info(
                "Movie -> {} ({}) \\{{}\\} [{}]",
                movie.title(),
                movie.year().orElse(null),
                movie.externalId(),
                file
        );
    }

    private void processEpisode(Path file, ParsedEpisode episode) {
        LOGGER.info(
                "Episode -> {} S{}E{} [{}]",
                episode.showTitle(),
                episode.season(),
                episode.episode(),
                file
        );
    }
}
