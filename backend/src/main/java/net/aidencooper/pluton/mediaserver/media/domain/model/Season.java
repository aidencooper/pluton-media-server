package net.aidencooper.pluton.mediaserver.media.domain.model;

import java.util.List;
import java.util.UUID;

public record Season(
        UUID showId,
        int season,
        List<Episode> episodes
) {}
