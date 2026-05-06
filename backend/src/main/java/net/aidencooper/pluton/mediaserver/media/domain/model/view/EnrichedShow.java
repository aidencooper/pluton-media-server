package net.aidencooper.pluton.mediaserver.media.domain.model.view;

import net.aidencooper.pluton.mediaserver.media.domain.model.Show;

public record EnrichedShow(
        Show show,
        ShowMetadata metadata
) {}
