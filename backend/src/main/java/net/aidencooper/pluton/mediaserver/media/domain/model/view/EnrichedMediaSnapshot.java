package net.aidencooper.pluton.mediaserver.media.domain.model.view;

import net.aidencooper.pluton.mediaserver.media.domain.model.Movie;

import java.util.List;

public record EnrichedMediaSnapshot(
        List<Movie> movies,
        List<EnrichedShow> shows
) {}
