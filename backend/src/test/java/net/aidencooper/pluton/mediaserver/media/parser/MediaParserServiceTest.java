package net.aidencooper.pluton.mediaserver.media.parser;

import net.aidencooper.pluton.mediaserver.media.parser.adapter.MediaParserService;
import net.aidencooper.pluton.mediaserver.media.parser.model.MovieInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MediaParserServiceTest {
    private final MediaParserService mediaParserService = new MediaParserService();

    @Test
    void parses_full_movie_metadata() {
        String input = "Avatar Fire and Ash (2025) {tmdb-83533}";

        MovieInfo movieInfo = this.mediaParserService.parseMovieFolder(input);

        Assertions.assertEquals("Avatar Fire and Ash", movieInfo.name());
        Assertions.assertEquals("2025", movieInfo.year().orElse(null));
        Assertions.assertEquals("83533", movieInfo.tmdbId().orElse(null));
    }

    @Test
    void parses_name_only() {
        String input = "Inception";

        MovieInfo movieInfo = this.mediaParserService.parseMovieFolder(input);

        Assertions.assertEquals("Inception", movieInfo.name());
        Assertions.assertTrue(movieInfo.year().isEmpty());
        Assertions.assertTrue(movieInfo.tmdbId().isEmpty());
    }
}
