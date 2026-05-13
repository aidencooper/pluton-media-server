package net.aidencooper.pluton.mediaserver.library.domain.request;

import lombok.Builder;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryType;

import java.util.Set;

@Builder
public record LibraryUpdateRequest(
   String name,
   LibraryType type,
   Set<String> folderPaths,
   boolean enabled
) {}