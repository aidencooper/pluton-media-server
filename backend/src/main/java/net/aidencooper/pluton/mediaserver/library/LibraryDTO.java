package net.aidencooper.pluton.mediaserver.library;

import java.util.List;

public record LibraryDTO(
   Long id,
   ContentType contentType,
   List<String> folderPaths,
   String displayName,
   boolean enabled
) {}
