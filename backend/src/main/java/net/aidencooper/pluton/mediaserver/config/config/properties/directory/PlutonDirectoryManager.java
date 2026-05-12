package net.aidencooper.pluton.mediaserver.config.config.properties.directory;

import net.aidencooper.pluton.mediaserver.config.config.properties.PlutonProperties;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class PlutonDirectoryManager {
    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return beanFactory -> {
            PlutonProperties properties = beanFactory.getBean(PlutonProperties.class);

            Path basePath = properties.getBasePath();

            for(PlutonDirectory config : PlutonDirectory.values()) {
                Path resolved = basePath.resolve(config.name().toLowerCase());

                properties.register(config, resolved);

                try {
                    Files.createDirectories(resolved);
                } catch (IOException exception) {
                    throw new RuntimeException("Unable to create Pluton directories", exception);
                }
            }
        };
    }
}
