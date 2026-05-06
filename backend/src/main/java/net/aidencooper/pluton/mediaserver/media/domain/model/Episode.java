package net.aidencooper.pluton.mediaserver.media.domain.model;

import java.util.UUID;

public record Episode(
        UUID showId,
        int season,
        int episode
) implements Media {}
