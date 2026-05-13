package net.aidencooper.pluton.mediaserver.library.domain;

import lombok.Builder;

import java.util.Set;

@Builder
public record LibraryDTO(
        Long id,
        String name,
        LibraryType type,
        Set<String> folderPaths,
        boolean enabled
) {}
