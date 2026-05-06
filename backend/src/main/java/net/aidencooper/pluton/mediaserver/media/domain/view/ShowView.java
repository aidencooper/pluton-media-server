package net.aidencooper.pluton.mediaserver.media.domain.view;

import java.util.List;
import java.util.UUID;

public record ShowView(
        UUID id,
        String title,
        String displayTitle,
        List<SeasonView> seasons
) {}
