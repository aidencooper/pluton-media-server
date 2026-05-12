package net.aidencooper.pluton.mediaserver.library;

import lombok.RequiredArgsConstructor;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryDTO;
import net.aidencooper.pluton.mediaserver.library.domain.Library;
import net.aidencooper.pluton.mediaserver.library.exception.LibraryNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        return this.libraryMapper.toDTO(savedLibrary);
    }

    public void updateLibrary(Long id, LibraryUpdateRequest request) {
        Library library = this.libraryRepository
                .findById(id)
                .orElseThrow(() -> new LibraryNotFoundException(id));

        library.setName(request.name());
        library.setType(request.type());
        library.setFolderPaths(request.folderPaths());
        library.setEnabled(request.enabled());
        library.setUpdatedAt(Instant.now());

        this.libraryRepository.save(library);
    }

    public void deleteLibrary(Long id) {
        try { this.libraryRepository.deleteById(id); }
        catch (IllegalArgumentException exception) { throw new LibraryNotFoundException(id); }
    }
}
