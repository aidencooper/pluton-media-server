package net.aidencooper.pluton.mediaserver.media.domain.view;

import java.util.List;

public record MediaSnapshotView(
        List<MovieView> movies,
        List<ShowView> shows
) {}
