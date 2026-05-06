package net.aidencooper.pluton.mediaserver.media.domain.model.view;

import net.aidencooper.pluton.mediaserver.media.domain.model.DisplayTitle;
import net.aidencooper.pluton.mediaserver.media.domain.model.Title;

public record ShowMetadata(
        Title title,
        DisplayTitle displayTitle
) {}
