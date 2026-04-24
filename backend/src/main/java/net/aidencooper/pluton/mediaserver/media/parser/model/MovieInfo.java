package net.aidencooper.pluton.mediaserver.media.parser.model;

import java.util.Optional;

public record MovieInfo(
        String name,
        Optional<String> year,
        Optional<String> tmdbId
) {}
