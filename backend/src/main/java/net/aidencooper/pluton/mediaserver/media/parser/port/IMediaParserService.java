package net.aidencooper.pluton.mediaserver.media.parser.port;

import net.aidencooper.pluton.mediaserver.media.parser.model.MovieInfo;

public interface IMediaParserService {
    MovieInfo parseMovieFolder(String folderName);
}
