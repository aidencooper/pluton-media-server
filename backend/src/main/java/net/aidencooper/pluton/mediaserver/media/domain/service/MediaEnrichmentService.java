package net.aidencooper.pluton.mediaserver.media.domain.service;

import net.aidencooper.pluton.mediaserver.media.domain.model.*;
import net.aidencooper.pluton.mediaserver.media.domain.view.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaEnrichmentService {
    public MediaSnapshotView enrich(MediaSnapshot snapshot) {
        List<MovieView> movies = snapshot.movies().stream()
                .map(this::enrichMovie)
                .toList();

        List<ShowView> shows = snapshot.shows().stream()
                .map(this::enrichShow)
                .toList();

        return new MediaSnapshotView(movies, shows);
    }

    private MovieView enrichMovie(Movie movie) {
        return new MovieView(
                movie.id(),
                movie.title().value(),
                movie.displayTitle().value(),
                movie.year(),
                this.fetchPoster(movie),
                this.fetchRating(movie)
        );
    }

    private ShowView enrichShow(Show show) {
        return new ShowView(
                show.id(),
                show.title().value(),
                show.displayTitle().value(),
                show.seasons().stream()
                        .map(this::enrichSeason)
                        .toList()
        );
    }

    private SeasonView enrichSeason(Season season) {
        return new SeasonView(
                season.season(),
                season.episodes().stream()
                        .map(this::enrichEpisode)
                        .toList()
        );
    }

    private EpisodeView enrichEpisode(Episode episode) {
        return new EpisodeView(
                episode.season(),
                episode.episode()
        );
    }

    private Optional<String> fetchPoster(Movie movie) {
        return Optional.empty();
    }

    private Optional<Float> fetchRating(Movie movie) {
        return Optional.empty();
    }
}
