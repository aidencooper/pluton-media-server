package net.aidencooper.pluton.mediaserver.media.domain.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.aidencooper.pluton.mediaserver.media.domain.model.*;
import net.aidencooper.pluton.mediaserver.media.domain.service.MovieIdentityService;
import net.aidencooper.pluton.mediaserver.media.domain.service.ShowIdentityService;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedEpisode;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMedia;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMovie;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Getter
public class MediaMapper {
    private final MovieIdentityService movieIdentityService;
    private final ShowIdentityService showIdentityService;

    public Movie toMovie(ParsedMovie movie) {
        UUID id = this.getMovieIdentityService().generateId(
                movie.title(),
                movie.year().orElse(null)
        );

        return new Movie(
                id,
                movie.title(),
                movie.externalId(),
                movie.year()
        );
    }

    public Episode toEpisode(ParsedEpisode episode) {
        UUID id = this.getShowIdentityService().generateId(
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
