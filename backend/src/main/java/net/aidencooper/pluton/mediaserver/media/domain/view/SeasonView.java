package net.aidencooper.pluton.mediaserver.media.domain.view;

import java.util.List;

public record SeasonView(
        int season,
        List<EpisodeView> episodes
) {}
