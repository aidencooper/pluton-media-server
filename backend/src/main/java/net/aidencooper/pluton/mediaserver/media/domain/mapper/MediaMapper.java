package net.aidencooper.pluton.mediaserver.media.domain.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.aidencooper.pluton.mediaserver.media.domain.model.*;
import net.aidencooper.pluton.mediaserver.media.domain.service.MediaIdentityService;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedEpisode;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMedia;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMovie;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Getter
public class MediaMapper {
    private final MediaIdentityService mediaIdentityService;

    public Movie toMovie(ParsedMovie movie) {
        UUID id = this.getMediaIdentityService().movieId(
                movie.title(),
                movie.year().orElse(null)
        );

        return new Movie(
                id,
                Title.of(movie.title()),
                DisplayTitle.of(movie.title()),
                movie.externalId(),
                movie.year()
        );
    }

    public Episode toEpisode(ParsedEpisode episode) {
        UUID id = this.getMediaIdentityService().showId(
                episode.showTitle()
        );

        return new Episode(
                id,
                episode.season(),
                episode.episode()
        );
    }

    public Media toDomain(ParsedMedia media) {
        return switch (media) {
            case ParsedMovie movie -> this.toMovie(movie);
            case ParsedEpisode episode -> this.toEpisode(episode);
        };
    }
}
