package net.aidencooper.pluton.mediaserver.media.domain.model;

import java.util.List;

public record MediaSnapshot(
        List<Movie> movies,
        List<Show> shows
) {}
