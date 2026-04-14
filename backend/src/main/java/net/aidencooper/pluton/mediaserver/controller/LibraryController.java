package net.aidencooper.pluton.mediaserver.controller;

import jakarta.validation.Valid;
import net.aidencooper.pluton.mediaserver.domain.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.domain.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryCreateRequestDTO;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryDTO;
import net.aidencooper.pluton.mediaserver.domain.dto.LibraryUpdateRequestDTO;
import net.aidencooper.pluton.mediaserver.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.mapper.adapter.LibraryMapper;
import net.aidencooper.pluton.mediaserver.service.adapter.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/libraries")
public class LibraryController {
    private final LibraryService libraryService;
    private final LibraryMapper libraryMapper;

    public LibraryController(LibraryService libraryService, LibraryMapper libraryMapper) {
        this.libraryService = libraryService;
        this.libraryMapper = libraryMapper;
    }

    @GetMapping
    public ResponseEntity<List<LibraryDTO>> getLibraries() {
        List<Library> libraries = this.libraryService.getLibraries();
        List<LibraryDTO> libraryDTOs = libraries.stream()
                .map(this.libraryMapper::toDTO)
                .toList();

        return ResponseEntity.ok(libraryDTOs);
    }

    @PostMapping
    public ResponseEntity<LibraryDTO> createLibrary(@Valid @RequestBody LibraryCreateRequestDTO requestDTO) {
        LibraryCreateRequest request = this.libraryMapper.fromDTO(requestDTO);
        Library library = this.libraryService.createLibrary(request);
        LibraryDTO libraryDTO = this.libraryMapper.toDTO(library);

        return new ResponseEntity<>(libraryDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<LibraryDTO> updateLibrary(
            @PathVariable Long id,
            @Valid @RequestBody LibraryUpdateRequestDTO requestDTO
    ) {
        LibraryUpdateRequest request = this.libraryMapper.fromDTO(requestDTO);
        Library library = this.libraryService.updateLibrary(id, request);
        LibraryDTO libraryDTO = this.libraryMapper.toDTO(library);

        return ResponseEntity.ok(libraryDTO);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable Long id) {
        this.libraryService.deleteLibrary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
