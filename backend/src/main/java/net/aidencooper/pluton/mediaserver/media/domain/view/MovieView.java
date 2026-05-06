package net.aidencooper.pluton.mediaserver.media.domain.view;

import java.util.Optional;
import java.util.UUID;

public record MovieView(
        UUID id,
        String title,
        String displayTitle,
        Optional<Integer> year,
        Optional<String> posterUrl,
        Optional<Float> rating
) {}
