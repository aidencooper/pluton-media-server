package net.aidencooper.pluton.mediaserver.domain;

import net.aidencooper.pluton.mediaserver.domain.entity.LibraryType;

import java.util.List;

public record LibraryCreateRequest(
        String name,
        LibraryType type,
        List<String> folderPaths,
        boolean enabled
) {}
