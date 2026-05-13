package net.aidencooper.pluton.mediaserver.library.application;

import net.aidencooper.pluton.mediaserver.library.domain.Library;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryDTO;
import net.aidencooper.pluton.mediaserver.library.domain.LibraryType;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryCreateRequest;
import net.aidencooper.pluton.mediaserver.library.domain.request.LibraryUpdateRequest;
import net.aidencooper.pluton.mediaserver.library.exception.LibraryNotFoundException;
import net.aidencooper.pluton.mediaserver.library.persistence.LibraryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTests {
    @Mock
    private LibraryMapper libraryMapper;

    @Mock
    private LibraryRepository libraryRepository;

    @InjectMocks
    private LibraryService libraryService;

    private Library library;
    private LibraryDTO libraryDTO;

    @BeforeEach
    public void setUp() {
        this.library = Library.builder()
                .id(1L)
                .name("Movie Library")
                .type(LibraryType.MOVIES)
                .folderPaths(Set.of(
                        "C:/Media/Movies",
                        "D:/Media/Movies"
                ))
                .enabled(true)
                .build();

        this.libraryDTO = LibraryDTO.builder()
                .id(1L)
                .name("Movie Library")
                .type(LibraryType.MOVIES)
                .folderPaths(Set.of(
                        "C:/Media/Movies",
                        "D:/Media/Movies"
                ))
                .enabled(true)
                .build();
    }

    @Nested
    class GetLibraryTests {
        @Test
        void shouldReturnMappedLibraryDTOs() {
            Mockito.when(libraryRepository.findAll()).thenReturn(List.of(library));
            Mockito.when(libraryMapper.toDTO(library)).thenReturn(libraryDTO);

            List<LibraryDTO> result = libraryService.getLibraries();

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.size());

            LibraryDTO resultDTO = result.get(0);

            Assertions.assertEquals(1L, resultDTO.id());
            Assertions.assertEquals("Movie Library", resultDTO.name());
            Assertions.assertEquals(LibraryType.MOVIES, resultDTO.type());
            Assertions.assertEquals(Set.of("C:/Media/Movies", "D:/Media/Movies"), resultDTO.folderPaths());
            Assertions.assertTrue(resultDTO.enabled());

            Mockito.verify(libraryRepository).findAll();
            Mockito.verify(libraryMapper).toDTO(library);
        }
    }

    @Nested
    class CreateLibraryTests {
        @Test
        void shouldCreateAndReturnLibraryDTO() {
            LibraryCreateRequest request = LibraryCreateRequest.builder()
                    .name("Movie Library")
                    .type(LibraryType.MOVIES)
                    .folderPaths(Set.of(
                            "C:/Media/Movies",
                            "D:/Media/Movies"
                    ))
                    .enabled(true)
                    .build();

            Mockito.when(libraryMapper.toEntity(request)).thenReturn(library);
            Mockito.when(libraryRepository.save(library)).thenReturn(library);
            Mockito.when(libraryMapper.toDTO(library)).thenReturn(libraryDTO);

            LibraryDTO result = libraryService.createLibrary(request);

            Assertions.assertNotNull(result);

            Assertions.assertEquals(1L, result.id());
            Assertions.assertEquals("Movie Library", result.name());
            Assertions.assertEquals(LibraryType.MOVIES, result.type());
            Assertions.assertEquals(Set.of("C:/Media/Movies", "D:/Media/Movies"), result.folderPaths());
            Assertions.assertTrue(result.enabled());

            Mockito.verify(libraryMapper).toEntity(request);
            Mockito.verify(libraryRepository).save(library);
            Mockito.verify(libraryMapper).toDTO(library);
        }
    }

    @Nested
    class UpdateLibraryTests {
        @Test
        void shouldUpdateExistingLibrary() {
            LibraryUpdateRequest request = LibraryUpdateRequest.builder()
                    .name("Show Library")
                    .type(LibraryType.TV)
                    .folderPaths(Set.of(
                            "C:/Media/Shows",
                            "D:/Media/Shows"
                    ))
                    .enabled(false)
                    .build();

            LibraryDTO updatedLibraryDTO = LibraryDTO.builder()
                    .id(1L)
                    .name("Show Library")
                    .type(LibraryType.TV)
                    .folderPaths(Set.of(
                            "C:/Media/Shows",
                            "D:/Media/Shows"
                    ))
                    .enabled(false)
                    .build();

            Mockito.when(libraryRepository.findById(1L)).thenReturn(Optional.of(library));
            Mockito.when(libraryRepository.save(library)).thenReturn(library);
            Mockito.when(libraryMapper.toDTO(library)).thenReturn(updatedLibraryDTO);

            LibraryDTO result = libraryService.updateLibrary(1L, request);

            Assertions.assertNotNull(result);

            Assertions.assertEquals(1L, result.id());
            Assertions.assertEquals("Show Library", result.name());
            Assertions.assertEquals(LibraryType.TV, result.type());
            Assertions.assertEquals(Set.of("C:/Media/Shows", "D:/Media/Shows"), result.folderPaths());
            Assertions.assertFalse(result.enabled());

            Mockito.verify(libraryRepository).findById(1L);
            Mockito.verify(libraryMapper).updateEntityFromRequest(request, library);
            Mockito.verify(libraryRepository).save(library);
            Mockito.verify(libraryMapper).toDTO(library);
        }

        @Test
        void shouldThrow_WhenNotFound() {
            LibraryUpdateRequest request = LibraryUpdateRequest.builder()
                    .name("Show Library")
                    .type(LibraryType.TV)
                    .folderPaths(Set.of(
                            "C:/Media/Shows",
                            "D:/Media/Shows"
                    ))
                    .enabled(false)
                    .build();

            Mockito.when(libraryRepository.findById(1L)).thenReturn(Optional.empty());

            Assertions.assertThrows(
                    LibraryNotFoundException.class,
                    () -> libraryService.updateLibrary(1L, request)
            );

            Mockito.verify(libraryRepository).findById(1L);
            Mockito.verify(libraryMapper, Mockito.never()).updateEntityFromRequest(request, library);
            Mockito.verify(libraryRepository, Mockito.never()).save(library);
        }
    }

    @Nested
    class DeleteLibraryTests {
        @Test
        void shouldCallRepository() {
            Mockito.doNothing().when(libraryRepository).deleteById(1L);

            libraryService.deleteLibrary(1L);

            Mockito.verify(libraryRepository).deleteById(1L);
        }

        @Test
        void shouldThrow_WhenIllegalArgumentOccurs() {
            Mockito.doThrow(IllegalArgumentException.class)
                    .when(libraryRepository)
                    .deleteById(1L);

            Assertions.assertThrows(
                    LibraryNotFoundException.class,
                    () -> libraryService.deleteLibrary(1L)
            );

            Mockito.verify(libraryRepository).deleteById(1L);
        }
    }
}
