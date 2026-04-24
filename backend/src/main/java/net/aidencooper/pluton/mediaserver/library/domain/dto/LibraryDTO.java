package net.aidencooper.pluton.mediaserver.library.domain.dto;

import net.aidencooper.pluton.mediaserver.library.domain.entity.LibraryType;

import java.util.Set;

public record LibraryDTO(
        Long id,
        String name,
        LibraryType type,
        Set<String> folderPaths,
        boolean enabled
) {}
