package net.aidencooper.pluton.mediaserver.library;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public ResponseEntity<List<Library>> getLibraries() {
        return ResponseEntity.ok(this.libraryService.get());
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<Library>> getEnabledLibraries() {
        return ResponseEntity.ok(this.libraryService.getEnabled());
    }

    @GetMapping("/contentType/{contentType}")
    public ResponseEntity<List<Library>> getLibrariesByContentType(@PathVariable ContentType contentType) {
        return ResponseEntity.ok(this.libraryService.getByContentType(contentType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getLibraryById(@PathVariable Long id) {
        return this.libraryService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {
        try {
            Library createdLibrary = this.libraryService.createLibrary(library);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLibrary);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary(@PathVariable Long id, @RequestBody Library library) {
        try {
            Library updatedLibrary = this.libraryService.updateLibrary(id, library);
            return ResponseEntity.ok(updatedLibrary);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable Long id) {
        try {
            this.libraryService.deleteLibrary(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getLibraryCount() {
        return ResponseEntity.ok(this.libraryService.countLibraries());
    }
}
