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
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MediaScannerService implements IMediaScannerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaScannerService.class);
    private static final Set<String> VIDEO_EXTENSIONS = Set.of(".mp4", ".mkv", ".avi", ".mov", ".wmv", ".flv", ".webm", ".m4v");
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

        Set<Path> folderPaths;
        try {
            folderPaths = library.getFolderPaths().stream().map(Paths::get).collect(Collectors.toSet());
        } catch (InvalidPathException exception) {
            return;
        }

        folderPaths.forEach(folder -> {
            if(!Files.exists(folder)) {
                LOGGER.warn("Folder \"" + folder.toAbsolutePath() + "\" from library id " + library.getId() + " does not exist");
                return;
            }

            int maxDepth = 3;
            try(Stream<Path> paths = Files.walk(folder, maxDepth).skip(1)) {
                paths.forEach(video -> {
                    if(Files.isDirectory(video)) {
                        System.out.println("FOLDER: \"" + video + "\"");
                    } else if(this.isVideoFile(video)) {
                        System.out.println("VIDEO: \"" + video + "\"");
                    } else {
                        System.out.println("INVALID: \"" + video + "\"");
                    }
                });
            } catch (IOException exception) {
                throw new RuntimeException("Error occurred when scanning library folders", exception);
            }
        });


    }

    private boolean isVideoFile(Path path) {
        if(Files.isDirectory(path)) return false;
        if(VIDEO_EXTENSIONS.stream().noneMatch(path.getFileName().toString().toLowerCase()::endsWith)) return false;

        return true;
    }

    private String getYearFromString(String string) {
        Pattern pattern = Pattern.compile("(19|20)\\\\d{2}");
        Matcher matcher = pattern.matcher(string);

        while(matcher.find()) return matcher.group();
        return null;
    }
}