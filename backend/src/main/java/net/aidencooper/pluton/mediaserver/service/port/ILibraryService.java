package net.aidencooper.pluton.mediaserver.service.port;

import net.aidencooper.pluton.mediaserver.domain.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.domain.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.domain.entity.Library;

import java.util.List;

public interface ILibraryService {
    List<Library> getLibraries();
    Library createLibrary(LibraryCreateRequest request);
    Library updateLibrary(Long id, LibraryUpdateRequest request);
    void deleteLibrary(Long id);
}
