package net.aidencooper.pluton.mediaserver.media.domain.model;

import java.util.Optional;
import java.util.UUID;

public record Movie(
        UUID id,
        String title,
        Optional<String> externalId,
        Optional<Integer> year
) implements Media {}
