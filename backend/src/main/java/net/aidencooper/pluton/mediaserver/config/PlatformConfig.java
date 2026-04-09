package net.aidencooper.pluton.mediaserver.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PlatformConfig {
    private final Path basePath;
    private final Path configPath;
    private final Path metadataPath;

    public PlatformConfig(Environment environment) {
        String os = System.getProperty("os.name").toLowerCase();

        this.basePath = environment.acceptsProfiles(Profiles.of("dev")) ? Paths.get("./pluton") // ./pluton
                : os.contains("win") ? Paths.get(System.getenv("PROGRAMDATA"), "Pluton") // C:\ProgramData\Pluton
                : os.contains("mac") ? Paths.get(System.getProperty("user.home"), "Library", "Application Support", "Pluton") : // ~/Library/Application Support/Pluton
                Paths.get(System.getProperty("user.home"), ".local", "share", "pluton"); // ~/.local/share/pluton
        this.configPath = this.basePath.resolve("config");
        this.metadataPath = this.basePath.resolve("metadata");
    }

    @PostConstruct
    public void createDirectories() {
        try {
            Files.createDirectories(this.basePath);
            Files.createDirectories(this.configPath);
            Files.createDirectories(this.metadataPath);
        } catch (IOException exception) {
            throw new RuntimeException("Error when creating the Pluton directories", exception);
        }
    }

    @Bean
    public Path getPlutonBasePath() {
        return this.basePath;
    }

    @Bean
    public Path getPlutonConfigPath() {
        return this.configPath;
    }

    @Bean
    public Path getPlutonMetadataPath() {
        return this.metadataPath;
    }
}
