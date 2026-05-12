package net.aidencooper.pluton.mediaserver.library.domain.request;

import net.aidencooper.pluton.mediaserver.library.domain.LibraryType;

import java.util.Set;

public record LibraryUpdateRequest(
   String name,
   LibraryType type,
   Set<String> folderPaths,
   boolean enabled
) {}