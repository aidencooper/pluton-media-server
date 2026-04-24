package net.aidencooper.pluton.mediaserver.library.service.adapter;

import net.aidencooper.pluton.mediaserver.library.domain.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.library.exception.LibraryNotFoundException;
import net.aidencooper.pluton.mediaserver.library.repository.LibraryRepository;
import net.aidencooper.pluton.mediaserver.library.service.port.ILibraryService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class LibraryService implements ILibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Override
    public List<Library> getLibraries() {
        return this.libraryRepository.findAll();
    }

    @Override
    public Library createLibrary(LibraryCreateRequest request) {
        Instant now = Instant.now();

        Library library = new Library(
                null,
                now,
                now,
                request.name(),
                request.type(),
                request.folderPaths(),
                request.enabled()
        );

        return this.libraryRepository.save(library);
    }

    @Override
    public Library updateLibrary(Long id, LibraryUpdateRequest request) {
        Library library = this.libraryRepository
                .findById(id)
                .orElseThrow(() -> new LibraryNotFoundException(id));

        library.setName(request.name());
        library.setType(request.type());
        library.setFolderPaths(request.folderPaths());
        library.setEnabled(request.enabled());

        library.setUpdatedAt(Instant.now());

        return this.libraryRepository.save(library);
    }

    @Override
    public void deleteLibrary(Long id) {
        try { this.libraryRepository.deleteById(id); }
        catch (IllegalArgumentException exception) { throw new LibraryNotFoundException(id); }
    }
}
