package net.aidencooper.pluton.mediaserver.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ConfigurationProperties(prefix = "pluton")
@Getter
@Setter
public class PlatformProperties {
    private static final Path DEFAULT_DEV_BASE_PATH =  Paths.get("./pluton").toAbsolutePath().normalize();
    private static final Path DEFAULT_WIN_BASE_PATH =  Paths.get(System.getenv("PROGRAMDATA"), "Pluton");
    private static final Path DEFAULT_MAC_BASE_PATH =  Paths.get(System.getProperty("user.home"), "Library", "Application Support", "Pluton");
    private static final Path DEFAULT_LINUX_BASE_PATH =  Paths.get(System.getProperty("user.home"), ".local", "share", "pluton");

    private final Environment environment;

    private Path basePath;
    private Path configPath;
    private Path metadataPath;

    public PlatformProperties(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void initPaths() {
        if(this.basePath == null) this.setBasePath(getDefaultBasePath());
        if(this.configPath == null) this.setConfigPath(this.basePath.resolve("config"));
        if(this.metadataPath == null) this.setMetadataPath(this.basePath.resolve("metadata"));

        this.createDirectories();
    }

    private Path getDefaultBasePath() {
        if(this.environment.acceptsProfiles(Profiles.of("dev"))) {
            return DEFAULT_DEV_BASE_PATH;
        }

        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")) return DEFAULT_WIN_BASE_PATH;
        else if(os.contains("mac")) return DEFAULT_MAC_BASE_PATH;
        else return DEFAULT_LINUX_BASE_PATH;
    }

    private void createDirectories() {
        try {
            Files.createDirectories(this.basePath);
            Files.createDirectories(this.configPath);
            Files.createDirectories(this.metadataPath);
        } catch (IOException exception) {
            throw new RuntimeException("Error creating the Pluton directories", exception);
        }
    }
}
