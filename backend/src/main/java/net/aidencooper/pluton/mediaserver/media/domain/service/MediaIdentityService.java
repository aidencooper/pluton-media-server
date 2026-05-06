package net.aidencooper.pluton.mediaserver.media.domain.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class MediaIdentityService {
    public UUID movieId(String title, Integer year) {
        return UUID.nameUUIDFromBytes(this.normalizeMovie(title, year).getBytes(StandardCharsets.UTF_8));
    }

    public UUID showId(String title) {
        return UUID.nameUUIDFromBytes(this.normalizeShow(title).getBytes(StandardCharsets.UTF_8));
    }

    private String normalizeMovie(String title, Integer year) {
        return title.toLowerCase().trim() + ":" + (year != null ? year : "unknown");
    }

    private String normalizeShow(String title) {
        return title.toLowerCase().trim();
    }
}
