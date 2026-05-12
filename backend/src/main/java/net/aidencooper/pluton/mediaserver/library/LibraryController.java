package net.aidencooper.pluton.mediaserver.library;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/libraries")
@RequiredArgsConstructor
@Getter
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<LibraryDTO>> getLibraries() {
        List<LibraryDTO> libraries = this.getLibraryService().getLibraries();
        return new ResponseEntity<>(libraries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LibraryDTO> createLibrary(@Valid @RequestBody LibraryCreateRequest request) {
        LibraryDTO libraryDTO = this.getLibraryService().createLibrary(request);
        return new ResponseEntity<>(libraryDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updateLibrary(
            @PathVariable Long id,
            @Valid @RequestBody LibraryUpdateRequest request
    ) {
        this.getLibraryService().updateLibrary(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable Long id) {
        this.getLibraryService().deleteLibrary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
