package net.aidencooper.pluton.mediaserver.library.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.aidencooper.pluton.mediaserver.library.domain.Library;
import net.aidencooper.pluton.mediaserver.library.persistence.LibraryRepository;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class LibraryProvider {
    private final LibraryRepository libraryRepository;

    public List<String> getEnabledLibraryPaths() {
        return this.getLibraryRepository()
                .findAll().stream()
                .filter(Library::isEnabled)
                .flatMap(library -> library.getFolderPaths().stream())
                .toList();
    }
}
