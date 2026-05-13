package net.aidencooper.pluton.mediaserver.config.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.aidencooper.pluton.mediaserver.config.properties.directory.PlutonDirectory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "pluton")
@Getter
@Setter
public class PlutonProperties {
    @Getter(AccessLevel.PRIVATE)
    private final Map<PlutonDirectory, Path> directories = new EnumMap<>(PlutonDirectory.class);

    private Path basePath = Path.of("./pluton");

    public void register(PlutonDirectory config, Path path) {
        this.getDirectories().put(config, path);
    }

    public Path getPath(PlutonDirectory config) {
        return this.getDirectories().get(config);
    }
}
