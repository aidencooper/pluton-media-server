package net.aidencooper.pluton.mediaserver.service.adapter;

import net.aidencooper.pluton.mediaserver.domain.entity.Library;
import net.aidencooper.pluton.mediaserver.domain.entity.LibraryType;
import net.aidencooper.pluton.mediaserver.service.port.ILibraryService;
import net.aidencooper.pluton.mediaserver.service.port.IMediaScannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class MediaScannerService implements IMediaScannerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaScannerService.class);

    private static final int MAX_DEPTH = 4;
    private static final Set<String> PIC_EXTENSIONS = Set.of(".jpg", ".jpeg", ".jpe", ".jfif", ".jfi", ".png", ".apng", ".gif", ".webp", ".bmp", ".dib", ".tiff", ".tif", ".heif", ".heic", ".hif", ".avif");
    private static final Set<String> VIDEO_EXTENSIONS = Set.of(".mp4", ".mkv", ".avi", ".mov", ".wmv", ".flv", ".webm", ".m4v");

    private static final Pattern TMDB_ID_PATTERN = Pattern.compile("\\[tmdbid-(\\d+)]");
    private static final Pattern YEAR_PATTERN = Pattern.compile("^((19|2[0-9])[0-9]{2})");

    private final ILibraryService libraryService;

    public  MediaScannerService(ILibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void scan() {
        this.libraryService.getLibraries().forEach(this::scan);
    }

    @Override
    public void scan(Library library) {
        LOGGER.info("Starting scan for library: [{}] {}", library.getId(), library.getName());

        library.getFolderPaths().parallelStream().forEach(folderPath -> {
            Path folder = Paths.get(folderPath);
            if(Files.exists(folder)) {
                try {
                    if (library.getType() == LibraryType.MOVIES) {
                        this.scanMoviesFolder(folder);
                    } else if (library.getType() == LibraryType.TV) {
                        this.scanTVFolder(folder);
                    } else {
                        LOGGER.warn("Library type {} not supported when scanning", library.getType());
                    }
                } catch (IOException exception) {
                    LOGGER.error("Could not scan folder path: {}", folder, exception);
                }
            }
        });
    }

    private void scanMoviesFolder(Path root) throws IOException {
        try(Stream<Path> paths = Files.walk(root, MAX_DEPTH).skip(1)) {
            paths.forEach(file -> {
                if(Files.isDirectory(file)) {
                    System.out.println("FOLDER: \"" + file + "\"");
                } else if(this.isVideoFile(file)) {
                    System.out.println("VIDEO: \"" + file + "\"");
                } else if(this.isPicFile(file)){
                    System.out.println("PIC: \"" + file + "\"");
                } else {
                    System.out.println("INVALID: \"" + file + "\"");
                }
            });
        }
    }

    private void scanTVFolder(Path root) throws IOException {
        try(Stream<Path> paths = Files.walk(root, MAX_DEPTH).skip(1)) {
            paths.forEach(file -> {
                if(Files.isDirectory(file)) {
                    System.out.println("FOLDER: \"" + file + "\"");
                } else if(this.isVideoFile(file)) {
                    System.out.println("VIDEO: \"" + file + "\"");
                } else if(this.isPicFile(file)){
                    System.out.println("PIC: \"" + file + "\"");
                } else {
                    System.out.println("INVALID: \"" + file + "\"");
                }
            });
        }
    }

    private void processMovieFile() {

    }

    private void processEpisodeFile() {

    }

    private boolean isPicFile(Path path) {
        if(Files.isDirectory(path)) return false;
        if(PIC_EXTENSIONS.stream().noneMatch(path.getFileName().toString().toLowerCase()::endsWith)) return false;

        return true;
    }

    private boolean isVideoFile(Path path) {
        if(Files.isDirectory(path)) return false;
        if(VIDEO_EXTENSIONS.stream().noneMatch(path.getFileName().toString().toLowerCase()::endsWith)) return false;

        return true;
    }

    private String getYearFromString(String string) {
        Pattern pattern = Pattern.compile("(19|20)\\\\d{2}");
        Matcher matcher = pattern.matcher(string);
        
        return matcher.find() ? matcher.group() : null;
    }
}

// MOVIES
// Level 1 = Folder Movie Name
// Level 2 = File Movie Name, Artwork

// Level 1 = File Movie Name

// SHOWS
// Level 1 = Folder Show Name
// Level 2 = Folder Season Name, Artwork
// Level 3 = File Episodes

// Level 1 = Folder Show Name
// Level 2 = File Episodes, Artwork