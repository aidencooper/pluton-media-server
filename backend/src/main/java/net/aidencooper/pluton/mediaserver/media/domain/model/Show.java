package net.aidencooper.pluton.mediaserver.media.domain.model;

import java.util.List;
import java.util.UUID;

public record Show(
        UUID id,
        Title title,
        DisplayTitle displayTitle,
        List<Season> seasons
) {}
