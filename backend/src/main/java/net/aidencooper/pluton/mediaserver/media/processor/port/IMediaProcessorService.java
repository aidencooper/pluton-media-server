package net.aidencooper.pluton.mediaserver.media.processor.port;

import net.aidencooper.pluton.mediaserver.media.parser.model.MovieInfo;

import java.nio.file.Path;

public interface IMediaProcessorService {
    void processMovieFile(Path file, MovieInfo movieInfo);
}
