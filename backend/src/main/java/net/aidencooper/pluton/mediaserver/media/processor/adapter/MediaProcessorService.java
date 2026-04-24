package net.aidencooper.pluton.mediaserver.media.processor.adapter;

import net.aidencooper.pluton.mediaserver.media.parser.model.MovieInfo;
import net.aidencooper.pluton.mediaserver.media.processor.port.IMediaProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class MediaProcessorService implements IMediaProcessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaProcessorService.class);

    @Override
    public void processMovieFile(Path file, MovieInfo movieInfo) {
        LOGGER.info(
                """
                   Processing movie:
                   File: {}
                   Name: {}
                   Year: {}
                   TMDB ID: {}
               """
        , file, movieInfo.name(), movieInfo.year(), movieInfo.tmdbId());
    }
}
