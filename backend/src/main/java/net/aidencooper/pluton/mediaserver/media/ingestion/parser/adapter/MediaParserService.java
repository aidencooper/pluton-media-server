package net.aidencooper.pluton.mediaserver.media.ingestion.parser.adapter;

import net.aidencooper.pluton.mediaserver.media.ingestion.classifier.MediaClassification;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedEpisode;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMedia;
import net.aidencooper.pluton.mediaserver.media.ingestion.model.ParsedMovie;
import net.aidencooper.pluton.mediaserver.media.ingestion.parser.port.IMediaParserService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MediaParserService implements IMediaParserService {
    private static final Pattern NAME_PATTERN = Pattern.compile("^(.*?)(?=\\s*(\\(|\\{|$))");
    private static final Pattern YEAR_PATTERN = Pattern.compile("\\((\\d{4})\\)");
    private static final Pattern TMDB_ID_PATTERN = Pattern.compile("\\{tmdb-(\\d+)}");
    private static final Pattern EPISODE_PATTERN = Pattern.compile(".*[sS](\\d{1,2})[eE](\\d{1,2}).*");

    @Override
    public ParsedMedia parse(Path path, MediaClassification classification) {
        String name = path.getFileName().toString();
        return switch(classification) {
            case MediaClassification.MOVIE -> this.parseMovie(name);
            case MediaClassification.EPISODE -> this.parseEpisode(name);
            case MediaClassification.UNKNOWN -> throw new IllegalArgumentException("Unknown media: " + name);
            case MediaClassification.IGNORE -> throw new IllegalArgumentException("Ignored media: " + name);
        };
    }

    private ParsedMovie parseMovie(String name) {
        return new ParsedMovie(
                this.extract(NAME_PATTERN, name).orElse(name),
                this.extract(TMDB_ID_PATTERN, name),
                this.extractInt(YEAR_PATTERN, name)
        );
    }

    private ParsedEpisode parseEpisode(String name) {
        Matcher matcher = EPISODE_PATTERN.matcher(name);
        if(!matcher.find()) throw new IllegalArgumentException("Invalid episode format: " + name);

        int season = Integer.parseInt(matcher.group(1));
        int episode = Integer.parseInt(matcher.group(2));

        return new ParsedEpisode(
                this.extract(NAME_PATTERN, name).orElse(name),
                season,
                episode
        );
    }

    private Optional<String> extract(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    private Optional<Integer> extractInt(Pattern pattern, String input) {
        return this.extract(pattern, input).map(Integer::parseInt);
    }
}
