package net.aidencooper.pluton.mediaserver.media.scanner;

import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.library.service.port.ILibraryService;
import net.aidencooper.pluton.mediaserver.media.filesystem.port.IFileSystemService;
import net.aidencooper.pluton.mediaserver.media.parser.model.MovieInfo;
import net.aidencooper.pluton.mediaserver.media.parser.port.IMediaParserService;
import net.aidencooper.pluton.mediaserver.media.processor.port.IMediaProcessorService;
import net.aidencooper.pluton.mediaserver.media.scanner.adapter.MediaScannerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@SpringBootTest
public class MediaScannerServiceTest {
    @MockitoBean IFileSystemService fileSystemService;
    @MockitoBean ILibraryService libraryService;
    @MockitoBean IMediaParserService mediaParserService;
    @MockitoBean IMediaProcessorService mediaProcessorService;

    @Autowired MediaScannerService mediaScannerService;

    @Test
    void scans_library_and_processes_movies() throws Exception {
        Path root = Path.of("Movies");
        Path folder = Path.of("Movies/F1 (2025");

        Library library = new Library();
        library.setFolderPaths(Set.of("Movies"));

        Mockito.when(this.libraryService.getLibraries()).thenReturn(List.of(library));
        Mockito.when(this.fileSystemService.exists(root)).thenReturn(true);
        Mockito.when(this.fileSystemService.walk(Mockito.eq(root), Mockito.anyInt())).thenReturn(Stream.of(folder));
        Mockito.when(this.fileSystemService.isDirectory(folder)).thenReturn(true);
        Mockito.when(this.fileSystemService.list(folder)).thenReturn(Stream.of(Path.of("F1.mkv")));
        Mockito.when(this.mediaParserService.parseMovieFolder(Mockito.anyString())).thenReturn(new MovieInfo(
                "F1",
                Optional.of("2025"),
                Optional.empty()
        ));

        this.mediaScannerService.scan();

        Mockito.verify(this.mediaProcessorService).processMovieFile(Mockito.any(), Mockito.any());
    }
}
