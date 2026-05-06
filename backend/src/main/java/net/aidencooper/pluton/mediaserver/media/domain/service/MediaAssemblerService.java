package net.aidencooper.pluton.mediaserver.media.domain.service;

import net.aidencooper.pluton.mediaserver.media.domain.model.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MediaAssemblerService {
    public MediaSnapshot assemble(List<Movie> movies, List<Episode> episodes) {
        Map<UUID, Map<Integer, List<Episode>>> byShowAndSeason = episodes.stream()
                .collect(Collectors.groupingBy(
                        Episode::showId,
                        Collectors.groupingBy(Episode::season)
                ));

        List<Show> shows = byShowAndSeason.entrySet().stream()
                .map(showEntry -> {
                    UUID showId = showEntry.getKey();

                    List<Season> seasons = showEntry.getValue().entrySet().stream()
                            .map(seasonEntry -> new Season(
                                    showId,
                                    seasonEntry.getKey(),
                                    this.sortEpisodes(seasonEntry.getValue())
                            )).sorted(Comparator.comparingInt(Season::season))
                            .toList();

                    String title = showEntry.getValue().values().stream()
                            .flatMap(List::stream)
                            .map(Episode::showTitle)
                            .findFirst()
                            .orElse("Unknown");

                    return new Show(
                            showId,
                            seasons
                    );
                }).sorted(Comparator.comparing(show -> show.displayTitle().value()))
                .toList();

        return new MediaSnapshot(movies, shows);
    }

    private List<Episode> sortEpisodes(List<Episode> episodes) {
        return episodes.stream()
                .sorted(Comparator.comparingInt(Episode::episode))
                .toList();
    }
}
