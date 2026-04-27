package net.aidencooper.pluton.mediaserver.media.domain.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class MovieIdentityService {
    public UUID generateId(String title, Integer year) {
        return UUID.nameUUIDFromBytes(
                this.normalize(title, year).getBytes(StandardCharsets.UTF_8)
        );
    }

    private String normalize(String title, Integer year) {
        return title.toLowerCase().trim() + ":" + (year != null ? year : "unknown");
    }
}
