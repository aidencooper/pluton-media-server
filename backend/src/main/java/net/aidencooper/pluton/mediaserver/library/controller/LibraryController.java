package net.aidencooper.pluton.mediaserver.library.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryCreateRequestDTO;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryDTO;
import net.aidencooper.pluton.mediaserver.library.domain.dto.LibraryUpdateRequestDTO;
import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.library.mapper.LibraryMapper;
import net.aidencooper.pluton.mediaserver.library.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/libraries")
@Getter
public class LibraryController {
    private final LibraryService libraryService;
    private final LibraryMapper libraryMapper;

    public LibraryController(LibraryService libraryService, LibraryMapper libraryMapper) {
        this.libraryService = libraryService;
        this.libraryMapper = libraryMapper;
    }

    @GetMapping
    public ResponseEntity<List<LibraryDTO>> getLibraries() {
        List<Library> libraries = this.getLibraryService().getLibraries();
        List<LibraryDTO> libraryDTOs = libraries.stream()
                .map(this.getLibraryMapper()::toDTO)
                .toList();

        return ResponseEntity.ok(libraryDTOs);
    }

    @PostMapping
    public ResponseEntity<LibraryDTO> createLibrary(@Valid @RequestBody LibraryCreateRequestDTO requestDTO) {
        LibraryCreateRequest request = this.getLibraryMapper().fromDTO(requestDTO);
        Library library = this.getLibraryService().createLibrary(request);
        LibraryDTO libraryDTO = this.getLibraryMapper().toDTO(library);

        return new ResponseEntity<>(libraryDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<LibraryDTO> updateLibrary(
            @PathVariable Long id,
            @Valid @RequestBody LibraryUpdateRequestDTO requestDTO
    ) {
        LibraryUpdateRequest request = this.getLibraryMapper().fromDTO(requestDTO);
        Library library = this.getLibraryService().updateLibrary(id, request);
        LibraryDTO libraryDTO = this.getLibraryMapper().toDTO(library);

        return ResponseEntity.ok(libraryDTO);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable Long id) {
        this.getLibraryService().deleteLibrary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
