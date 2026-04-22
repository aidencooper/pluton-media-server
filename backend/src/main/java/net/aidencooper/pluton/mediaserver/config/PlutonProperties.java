package net.aidencooper.pluton.mediaserver.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ConfigurationProperties(prefix = "pluton")
@Getter
@Setter
public class PlutonProperties {
    private final Environment environment;

    private Path basePath;
    private Path cachePath;
    private Path configPath;
    private Path dataPath;
    private Path metadataPath;

    public PlutonProperties(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        if(this.getBasePath() == null) this.setBasePath(Paths.get(".").toAbsolutePath().normalize());
        if(this.getCachePath() == null) this.setCachePath(this.basePath.resolve("cache"));
        if(this.getConfigPath() == null) this.setConfigPath(this.basePath.resolve("config"));
        if(this.getDataPath() == null) this.setDataPath(this.basePath.resolve("data"));
        if(this.getMetadataPath() == null) this.setMetadataPath(this.basePath.resolve("metadata"));

        this.createDirectories();
    }

    private void createDirectories() {
        try {
            Files.createDirectories(this.getCachePath());
            Files.createDirectories(this.getConfigPath());
            Files.createDirectories(this.getDataPath());
            Files.createDirectories(this.getMetadataPath());
        } catch (IOException exception) {
            throw new RuntimeException("Unable to create Pluton directories", exception);
        }
    }
}
