package net.aidencooper.pluton.mediaserver.media.parser.adapter;

import net.aidencooper.pluton.mediaserver.media.parser.model.MovieInfo;
import net.aidencooper.pluton.mediaserver.media.parser.port.IMediaParserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MediaParserService implements IMediaParserService {
    private static final Pattern NAME_PATTERN = Pattern.compile("^(.*?)(?=\\s*(\\(|\\{|$))");
    private static final Pattern YEAR_PATTERN = Pattern.compile("\\((\\d{4})\\)");
    private static final Pattern TMDB_ID_PATTERN = Pattern.compile("\\{tmdb-(\\d+)}");

    @Override
    public MovieInfo parseMovieFolder(String folderName) {
        return new MovieInfo(
                this.extract(NAME_PATTERN, folderName).orElse(folderName),
                this.extract(YEAR_PATTERN, folderName),
                this.extract(TMDB_ID_PATTERN, folderName)
        );
    }

    private Optional<String> extract(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? Optional.ofNullable(matcher.group(1)) : Optional.empty();
    }
}
