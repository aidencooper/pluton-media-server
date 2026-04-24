package net.aidencooper.pluton.mediaserver.library.domain;

import net.aidencooper.pluton.mediaserver.library.domain.entity.LibraryType;

import java.util.Set;

public record LibraryCreateRequest(
        String name,
        LibraryType type,
        Set<String> folderPaths,
        boolean enabled
) {}
