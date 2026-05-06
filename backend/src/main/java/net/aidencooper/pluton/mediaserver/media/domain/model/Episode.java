package net.aidencooper.pluton.mediaserver.media.domain.model;

import java.util.UUID;

public record Episode(
        UUID showId,
        String showTitle,
        int season,
        int episode
) implements Media {}
