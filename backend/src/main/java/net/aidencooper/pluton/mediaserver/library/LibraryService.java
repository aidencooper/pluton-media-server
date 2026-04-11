package net.aidencooper.pluton.mediaserver.library;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public LibraryService(LibraryRepository libraryRepository, ModelMapper modelMapper) {
        this.libraryRepository = libraryRepository;
        this.modelMapper = modelMapper;
    }

    public Library createLibrary(Library library) {
        return this.libraryRepository.save(library);
    }

    public Library updateLibrary(Long id, Library libraryDetails) {
        Library library = this.libraryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Library not found with id: " + id));

        library.setDisplayName(libraryDetails.getDisplayName());
        library.setContentType(libraryDetails.getContentType());
        library.setFolderPaths(libraryDetails.getFolderPaths());
        library.setEnabled(libraryDetails.isEnabled());

        return this.libraryRepository.save(library);
    }

    public void deleteLibrary(Long id) {
        if(!this.libraryRepository.existsById(id)) throw new IllegalArgumentException("Library not found with id: " + id);
        this.libraryRepository.deleteById(id);
    }

    public List<Library> get() {
        return this.libraryRepository.findAll();
    }

    public List<Library> getEnabled() {
        return this.libraryRepository.findByEnabledTrue();
    }

    public List<Library> getByContentType(ContentType contentType) {
        return this.libraryRepository.findByContentType(contentType);
    }

    public List<Library> getEnabledByContentType(ContentType contentType) {
        return this.libraryRepository.findEnabledByContentType(contentType);
    }

    public Optional<Library> getById(Long id) {
        return this.libraryRepository.findById(id);
    }

    public Optional<Library> getByName(String displayName) {
        return this.libraryRepository.findByDisplayName(displayName);
    }

    public List<Library> getByPath(String folderPath) {
        return this.libraryRepository.findByFolderPath(folderPath);
    }

    public boolean exists(Long id) {
        return this.libraryRepository.existsById(id);
    }

    public boolean existsByName(String displayName) {
        return this.libraryRepository.existsByDisplayName(displayName);
    }

    public boolean existsByPath(String folderPath) {
        return this.libraryRepository.existsByFolderPath(folderPath);
    }

    public long countLibraries() {
        return this.libraryRepository.count();
    }

    public LibraryDTO getLibrary(Long id) {
        return this.modelMapper.map(this.libraryRepository.getReferenceById(id), LibraryDTO.class);
    }
}
