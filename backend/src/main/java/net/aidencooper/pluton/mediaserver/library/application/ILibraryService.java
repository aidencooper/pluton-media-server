package net.aidencooper.pluton.mediaserver.library.application;

import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryDTO;

import java.util.List;

public interface ILibraryService {
    List<LibraryDTO> getLibraries();
    LibraryDTO createLibrary(LibraryCreateRequest request);
    LibraryDTO updateLibrary(Long id, LibraryUpdateRequest request);
    void deleteLibrary(Long id);
}
