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
        // <Show UUID, <Season Number, Episode List>>
        Map<UUID, Map<Integer, List<Episode>>> byShowAndSeason = episodes.stream()
                .collect(Collectors.groupingBy(
                        Episode::showId, // Group episodes by show
                        Collectors.groupingBy(Episode::season) // Group episodes by season
                ));

        // Map UUID's to the Show domain object
        List<Show> shows = byShowAndSeason.entrySet().stream()
                .map(showEntry -> {
                    UUID showId = showEntry.getKey();

                    List<Season> seasons = showEntry.getValue().entrySet().stream()
                            .map(seasonEntry -> new Season(
                                    showId,
                                    seasonEntry.getKey(),
                                    seasonEntry.getValue().stream() // Sort episodes in season
                                            .sorted(Comparator.comparingInt(Episode::episode))
                                            .toList()
                            )).sorted(Comparator.comparingInt(Season::season))
                            .toList();

                    return new Show(
                            showId,
                            Title.of("Unknown"),
                            DisplayTitle.of("Unknown"),
                            seasons
                    );
                }).sorted(Comparator.comparing(Show::id))
                .toList();

        return new MediaSnapshot(movies, shows);
    }
}
