package net.aidencooper.pluton.mediaserver.media.scanner.adapter;

import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.library.service.port.ILibraryService;
import net.aidencooper.pluton.mediaserver.media.filesystem.port.IFileSystemService;
import net.aidencooper.pluton.mediaserver.media.parser.model.MovieInfo;
import net.aidencooper.pluton.mediaserver.media.parser.port.IMediaParserService;
import net.aidencooper.pluton.mediaserver.media.processor.port.IMediaProcessorService;
import net.aidencooper.pluton.mediaserver.media.scanner.port.IMediaScannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class MediaScannerService implements IMediaScannerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaScannerService.class);
    private static final int MAX_DEPTH = 4;
    private static final Set<String> VIDEO_EXTENSIONS = Set.of(
            ".avi", ".flv", ".m4v", ".mkv",
            ".mov", ".mp4", ".webm", ".wmv"
    );

    private final IFileSystemService fileSystemService;
    private final ILibraryService libraryService;
    private final IMediaParserService mediaParserService;
    private final IMediaProcessorService mediaProcessorService;

    public  MediaScannerService(
            IFileSystemService fileSystemService,
            ILibraryService libraryService,
            IMediaParserService mediaParserService,
            IMediaProcessorService mediaProcessorService
    ) {
        this.fileSystemService = fileSystemService;
        this.libraryService = libraryService;
        this.mediaParserService = mediaParserService;
        this.mediaProcessorService = mediaProcessorService;
    }

    @Override
    public void scan() {
        this.libraryService.getLibraries().forEach(this::scan);
    }

    private void scan(Library library) {
        library.getFolderPaths().forEach(folderPath -> {
            Path root = Paths.get(folderPath);

            if(!this.fileSystemService.exists(root)) {
                LOGGER.warn("Missing path: {}", root);
                return;
            }

            try(Stream<Path> paths = this.fileSystemService.walk(root, MAX_DEPTH)) {
                paths.filter(this.fileSystemService::isDirectory).forEach(this::processFolder);
            } catch (IOException exception) {
                LOGGER.error("Scan failed for {}", root, exception);
            }
        });
    }

    private void processFolder(Path folder) {
        String name = folder.getFileName().toString();
        MovieInfo movieInfo = this.mediaParserService.parseMovieFolder(name);

        try(Stream<Path> files = this.fileSystemService.list(folder)) {
            files.filter(this::isVideoFile).forEach(file -> this.mediaProcessorService.processMovieFile(file, movieInfo));
        } catch (IOException exception) {
            LOGGER.error("Folder scan failed for {}", folder, exception);
        }
    }

    private boolean isVideoFile(Path path) {
        return VIDEO_EXTENSIONS.stream().anyMatch(path.getFileName().toString().toLowerCase()::endsWith);
    }
}