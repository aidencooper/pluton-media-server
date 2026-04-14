package net.aidencooper.pluton.mediaserver.domain.dto;

import net.aidencooper.pluton.mediaserver.domain.entity.LibraryType;

import java.util.List;

public record LibraryDTO(
        Long id,
        String name,
        LibraryType type,
        List<String> folderPaths,
        boolean enabled
) {}
