package net.aidencooper.pluton.mediaserver.library;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public ResponseEntity<List<LibraryDTO>> getLibraries() {
        return ResponseEntity.ok(this.libraryService.get().stream()
                .map(library -> this.libraryService.getLibrary(
                        library.getId()
                )).collect(Collectors.toList()));
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<LibraryDTO>> getEnabledLibraries() {
        return ResponseEntity.ok(this.libraryService.getEnabled().stream()
                .map(library -> this.libraryService.getLibrary(
                        library.getId()
                )).collect(Collectors.toList()));
    }

    @GetMapping("/contentType/{contentType}")
    public ResponseEntity<List<LibraryDTO>> getLibrariesByContentType(@PathVariable ContentType contentType) {
        return ResponseEntity.ok(this.libraryService.getByContentType(contentType).stream()
                .map(library -> this.libraryService.getLibrary(
                        library.getId()
                )).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryDTO> getLibraryById(@PathVariable Long id) {
        return this.libraryService.getById(id)
                .map(library -> ResponseEntity.ok(this.libraryService.getLibrary(
                        library.getId()
                ))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LibraryDTO> createLibrary(@RequestBody Library library) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(this.libraryService.getLibrary(
                            this.libraryService.createLibrary(library).getId()
                    ));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryDTO> updateLibrary(@PathVariable Long id, @RequestBody Library library) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(this.libraryService.getLibrary(
                            this.libraryService.updateLibrary(id, library).getId()
                    ));
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
