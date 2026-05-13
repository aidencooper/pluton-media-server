package net.aidencooper.pluton.mediaserver.library.application;

import lombok.RequiredArgsConstructor;
import net.aidencooper.pluton.mediaserver.library.persistence.LibraryRepository;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryDTO;
import net.aidencooper.pluton.mediaserver.library.domain.Library;
import net.aidencooper.pluton.mediaserver.library.exception.LibraryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService implements ILibraryService {
    private final LibraryMapper libraryMapper;
    private final LibraryRepository libraryRepository;

    @Override
    public List<LibraryDTO> getLibraries() {
        return this.libraryRepository.findAll().stream()
                .map(this.libraryMapper::toDTO)
                .toList();
    }

    public LibraryDTO createLibrary(LibraryCreateRequest request) {
        Library library = this.libraryMapper.toEntity(request);
        Library savedLibrary = this.libraryRepository.save(library);
        LibraryDTO libraryDTO = this.libraryMapper.toDTO(savedLibrary);
        return libraryDTO;
    }

    public LibraryDTO updateLibrary(Long id, LibraryUpdateRequest request) {
        Library library = this.libraryRepository
                .findById(id)
                .orElseThrow(() -> new LibraryNotFoundException(id));

        this.libraryMapper.updateEntityFromRequest(request, library);
        Library updatedLibrary = this.libraryRepository.save(library);
        LibraryDTO libraryDTO = this.libraryMapper.toDTO(updatedLibrary);
        return libraryDTO;
    }

    public void deleteLibrary(Long id) {
        try { this.libraryRepository.deleteById(id); }
        catch (IllegalArgumentException exception) { throw new LibraryNotFoundException(id); }
    }
}
