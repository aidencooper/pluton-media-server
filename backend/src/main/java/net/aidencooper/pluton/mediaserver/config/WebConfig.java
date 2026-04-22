package net.aidencooper.pluton.mediaserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    private final PlutonProperties plutonProperties;
//
//    public WebConfig(PlutonProperties plutonProperties) {
//        this.plutonProperties = plutonProperties;
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/movies/**").addResourceLocations("file:" + plutonProperties.getMediaPath().resolve("Movies"));
//        registry.addResourceHandler("/streams/**").addResourceLocations("file:" + plutonProperties.getMediaPath().resolve("Streams"));
//    }
}
