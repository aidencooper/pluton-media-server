package net.aidencooper.pluton.mediaserver.service.adapter;

import net.aidencooper.pluton.mediaserver.domain.entity.Library;
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

@Service
public class MediaScannerService implements IMediaScannerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaScannerService.class);

    private static final Pattern YEAR_PATTERN = Pattern.compile("^((19|2[0-9])[0-9]{2})");

    @Override
    public void scanLibrary(Library library) {
        LOGGER.info("Starting scan for library: [{}] {}", library.getId(), library.getName());

        Set<Path> folderPaths;
        try {
            folderPaths = library.getFolderPaths().stream().map(Paths::get).collect(Collectors.toSet());
        } catch (InvalidPathException exception) {
            return;
        }

        folderPaths.forEach(folder -> {

        });


    }

    private String getYearFromString(String string) {
        Pattern pattern = Pattern.compile("(19|20)\\\\d{2}");
        Matcher matcher = pattern.matcher(string);

        while(matcher.find()) return matcher.group();
        return null;
    }
}
