package net.aidencooper.pluton.mediaserver.media.ingestion.model;

public record ParsedEpisode(
        String showTitle,
        int season,
        int episode
) implements ParsedMedia {}
