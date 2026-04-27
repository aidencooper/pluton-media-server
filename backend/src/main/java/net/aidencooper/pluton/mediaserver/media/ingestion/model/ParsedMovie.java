package net.aidencooper.pluton.mediaserver.media.ingestion.model;

import java.util.Optional;

public record ParsedMovie(
        String title,
        Optional<String> externalId,
        Optional<Integer> year
) implements ParsedMedia {}
