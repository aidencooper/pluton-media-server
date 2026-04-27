package net.aidencooper.pluton.mediaserver.media.ingestion.scanner.adapter;

import lombok.RequiredArgsConstructor;
import net.aidencooper.pluton.mediaserver.library.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.media.filesystem.port.IFileSystemService;
import net.aidencooper.pluton.mediaserver.media.ingestion.scanner.port.IMediaScannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MediaScannerService implements IMediaScannerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaScannerService.class);
    private static final int MAX_DEPTH = 4;
    private static final Set<String> VIDEO_EXTENSIONS = Set.of(
            ".avi", ".flv", ".m4v", ".mkv",
            ".mov", ".mp4", ".webm", ".wmv"
    );

    private final IFileSystemService fileSystemService;

    @Override
    public Stream<Path> scan(Library library) {
        return library.getFolderPaths().stream()
                .map(Paths::get)
                .filter(this.fileSystemService::exists)
                .flatMap(this::walkFolders)
                .flatMap(this::listVideoFiles);
    }

    private Stream<Path> walkFolders(Path root) {
        try {
            return this.fileSystemService.walk(root, MAX_DEPTH)
                    .filter(this.fileSystemService::isDirectory);
        } catch (IOException exception) {
            LOGGER.error("Scan failed for {}", root, exception);
            return Stream.empty();
        }
    }

    private Stream<Path> listVideoFiles(Path folder) {
        try {
            return this.fileSystemService.list(folder)
                    .filter(this::isVideoFile);
        } catch (IOException exception) {
            LOGGER.error("Folder scan failed for {}", folder, exception);
            return Stream.empty();
        }
    }

    private boolean isVideoFile(Path path) {
        return VIDEO_EXTENSIONS.stream().anyMatch(path.getFileName().toString().toLowerCase()::endsWith);
    }
}