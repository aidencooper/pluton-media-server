package net.aidencooper.pluton.mediaserver.library.domain;

import java.util.Set;

public record LibraryDTO(
        Long id,
        String name,
        LibraryType type,
        Set<String> folderPaths,
        boolean enabled
) {}
