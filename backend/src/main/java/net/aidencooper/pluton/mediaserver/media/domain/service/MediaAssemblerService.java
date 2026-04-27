package net.aidencooper.pluton.mediaserver.media.domain.service;

import net.aidencooper.pluton.mediaserver.media.domain.model.Episode;
import net.aidencooper.pluton.mediaserver.media.domain.model.Season;
import net.aidencooper.pluton.mediaserver.media.domain.model.Show;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MediaAssemblerService {
    public List<Show> assembleShows(List<Episode> episodes, Map<UUID, String> shows) {
        Map<UUID, Map<Integer, List<Episode>>> combined = episodes.stream()
                .collect(Collectors.groupingBy(
                        Episode::showId,
                        Collectors.groupingBy(Episode::season)
                ));

        return combined.entrySet().stream()
                .map(showEntry -> {
                    UUID showId = showEntry.getKey();
                    String title = shows.getOrDefault(showId, "Unknown");

                    List<Season> seasons = showEntry.getValue().entrySet().stream()
                            .map(seasonEntry -> new Season(
                                    showId,
                                    seasonEntry.getKey(),
                                    this.sortEpisodes(seasonEntry.getValue())
                            )).sorted(Comparator.comparingInt(Season::season))
                            .toList();

                    return new Show(showId, title, seasons);
                }).sorted(Comparator.comparing(Show::title))
                .toList();
    }

    private List<Episode> sortEpisodes(List<Episode> episodes) {
        return episodes.stream()
                .sorted(Comparator.comparingInt(Episode::episode))
                .toList();
    }
}
