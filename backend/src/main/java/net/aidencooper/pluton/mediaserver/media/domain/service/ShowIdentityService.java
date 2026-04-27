package net.aidencooper.pluton.mediaserver.media.domain.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class ShowIdentityService {
    public UUID generateId(String title) {
        return UUID.nameUUIDFromBytes(this.normalize(title).getBytes(StandardCharsets.UTF_8));
    };

    private String normalize(String title) {
        return title.toLowerCase().trim();
    }
}
